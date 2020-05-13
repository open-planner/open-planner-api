package br.edu.ifpb.mestrado.openplanner.api.application.service;

import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.assertAuditingFields;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.assertPage;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.assertPageNoContent;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.createSpecification;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.mockAuthenticationForAuditing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.BusinessException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.DuplicateKeyException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InvalidActualPasswordException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InvalidPasswordException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InvalidTokenException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.NotAuthenticatedUserException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Senha;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.PermissaoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.UsuarioRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.BaseEntitySpecification;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.util.BcryptUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.service.MailService;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.email.MailRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.UsuarioBuilder;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.UsuarioFilterRequestTOBuilder;
import br.edu.ifpb.mestrado.openplanner.api.test.util.UsuarioTestUtils;

@ActiveProfiles("test")
@SpringBootTest
public class UsuarioServiceImplTest {

    private static final String MOCK_EMAIL_ADMIN = "admin@email.com";
    private static final String MOCK_EMAIL_ROOT = "root@email.com";

    private UsuarioService usuarioService;

    private UsuarioRepository usuarioRepository;

    private PermissaoRepository permissaoRepository;

    @MockBean
    private MailService mailService;

    @Autowired
    public UsuarioServiceImplTest(UsuarioService usuarioService, UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockAuthenticationForAuditing(MOCK_EMAIL_ADMIN);
        doNothing().when(mailService).send(any(MailRequestTO.class));

        usuarioRepository.save(UsuarioTestUtils.create("João Paulo de Lima", "joao.lima@email.com", findPermissoesByIds(Permissao.ID_ADMIN)));
        usuarioRepository.save(UsuarioTestUtils.create("Maria da Silva", "maria.silva@email.com"));
        usuarioRepository.save(UsuarioTestUtils.createPendente("José de Souza", "jose.souza@email.com"));
        usuarioRepository.save(UsuarioTestUtils.createPendente("José de Paula", "jose.paula@email.com"));
        usuarioRepository.save(UsuarioTestUtils.createBloqueado("Isabel dos Santos", "isabel.santos@email.com"));
    }

    @AfterEach
    public void tearDown() throws Exception {
        Specification<Usuario> specification = BaseEntitySpecification.idGreaterThan(Usuario.ID_ADMIN);
        usuarioRepository.deleteAll(usuarioRepository.findAll(specification));
    }

    @Test
    public void testFindById() {
        Usuario usuario = usuarioService.findById(Usuario.ID_ADMIN);

        assertIsAdmin(usuario);
    }

    @Test
    public void testFindById_whenNotFound() {
        assertThrows(InformationNotFoundException.class, () -> usuarioService.findById(999L));
    }

    @Test
    public void findByEmail() {
        Usuario usuario = usuarioService.findByEmail("jose.paula@email.com");

        assertThat(usuario.getNome()).isEqualTo("José de Paula");
    }

    @Test
    public void findByEmail_whenNotFound() {
        assertThrows(InformationNotFoundException.class, () -> usuarioService.findByEmail("test@email.com"));
    }

    @Test
    public void findByAtivacaoToken() {
        Usuario usuario = usuarioService.findByAtivacaoToken(UsuarioTestUtils.MOCK_TOKEN_PREFIX + "jose.souza@email.com");

        assertThat(usuario.getNome()).isEqualTo("José de Souza");
    }

    @Test
    public void findByAtivacaoToken_whenNotFound() {
        assertThrows(InformationNotFoundException.class, () -> usuarioService.findByEmail("test@email.com"));
    }

    @Test
    public void findBySenhaResetToken() {
        Usuario usuario = usuarioService.findBySenhaResetToken(UsuarioTestUtils.MOCK_TOKEN_PREFIX + "isabel.santos@email.com");

        assertThat(usuario.getNome()).isEqualTo("Isabel dos Santos");
    }

    @Test
    public void findBySenhaResetToken_whenNotFound() {
        assertThrows(InvalidTokenException.class, () -> usuarioService.findBySenhaResetToken("token?jose.souza"));
    }

    @Test
    public void testFindAllByPageable() {
        Page<Usuario> usuariosPage = usuarioService.findAll(PageRequest.of(0, 10));

        assertPage(usuariosPage, 10, 0, 6, 1, 6);
    }

    @Test
    public void testFindAllByPageable_whenRoot() {
        mockAuthenticationForAuditing(MOCK_EMAIL_ROOT);
        Page<Usuario> usuariosPage = usuarioService.findAll(PageRequest.of(0, 10));

        assertPage(usuariosPage, 10, 0, 8, 1, 8);
    }

    @Test
    public void testFindAllBySpecificationAndPageable_filterById() {
        UsuarioFilterRequestTO filter = new UsuarioFilterRequestTOBuilder()
                .withId(Usuario.ID_ADMIN)
                .build();

        Page<Usuario> usuariosPage = usuarioService.findAll(createSpecification(filter, Usuario.class), PageRequest.of(0, 10));

        assertPage(usuariosPage, 10, 0, 1, 1, 1);
        assertIsAdmin(usuariosPage.getContent().get(0));
    }

    @Test
    public void testFindAllBySpecificationAndPageable_filterByNome() {
        UsuarioFilterRequestTO filter = new UsuarioFilterRequestTOBuilder()
                .withNome("jose souza")
                .build();

        Page<Usuario> usuariosPage = usuarioService.findAll(createSpecification(filter, Usuario.class), PageRequest.of(0, 10));

        assertPage(usuariosPage, 10, 0, 1, 1, 1);
    }

    @Test
    public void testFindAllBySpecificationAndPageable_filterByEmail() {
        UsuarioFilterRequestTO filter = new UsuarioFilterRequestTOBuilder()
                .withEmail("jose")
                .build();

        Page<Usuario> usuariosPage = usuarioService.findAll(createSpecification(filter, Usuario.class), PageRequest.of(0, 10));

        assertPage(usuariosPage, 10, 0, 2, 1, 2);
    }

    @Test
    public void testFindAllBySpecificationAndPageable_filterByAtivo() {
        UsuarioFilterRequestTO filter = new UsuarioFilterRequestTOBuilder()
                .withAtivo(true)
                .build();

        Page<Usuario> usuariosPage = usuarioService.findAll(createSpecification(filter, Usuario.class), PageRequest.of(0, 10));

        assertPage(usuariosPage, 10, 0, 3, 1, 3);
    }

    @Test
    public void testFindAllBySpecificationAndPageable_notFound() {
        UsuarioFilterRequestTO filter = new UsuarioFilterRequestTOBuilder()
                .withNome("alberto")
                .build();

        Page<Usuario> usuariosPage = usuarioService.findAll(createSpecification(filter, Usuario.class), PageRequest.of(0, 10));

        assertPageNoContent(usuariosPage, 10, 0);
    }

    @Test
    public void findAllActive() {
        List<Usuario> usuarios = usuarioService.findAllActive();

        assertThat(usuarios).hasSize(2);
    }

    @Test
    public void testGetAutenticado() {
        Usuario usuario = usuarioService.getAutenticado();

        assertIsAdmin(usuario);
    }

    @Test
    public void testGetAutenticado_whenNotAuthenticated() {
        mockAuthenticationForAuditing(null);

        assertThrows(NotAuthenticatedUserException.class, () -> usuarioService.getAutenticado());
    }

    @Test
    public void testSave_withSenha() {
        Usuario usuario = new UsuarioBuilder()
                .withNome("Miguel Lima")
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail("miguel.LIMA@email.com")
                .withSenha(new Senha(UsuarioTestUtils.MOCK_SENHA_PREFIX + "miguel.lima@email.com"))
                .build();
        usuarioService.save(usuario);

        verifySendMail(1);
        assertThat(usuario.getId()).isNotNull();
        assertThat(usuario.getNome()).isEqualTo("Miguel Lima");
        assertThat(usuario.getDataNascimento()).isEqualTo(LocalDate.now().minusYears(20));
        assertThat(usuario.getEmail()).isEqualTo("miguel.lima@email.com");
        assertThat(usuario.getPendente()).isTrue();
        assertThat(usuario.getBloqueado()).isFalse();
        assertThat(usuario.getSenha().getValor()).isNotNull();
        assertThat(usuario.getSenha().getResetToken()).isNull();
        assertThat(usuario.getAtivacaoToken()).isNotNull();
        assertAuditingFields(usuario, MOCK_EMAIL_ADMIN);
    }

    @Test
    public void testAtivacao() {
        Usuario usuario = usuarioService.activate(UsuarioTestUtils.MOCK_TOKEN_PREFIX + "jose.souza@email.com");

        assertThat(usuario.getId()).isNotNull();
        assertThat(usuario.getNome()).isEqualTo("José de Souza");
        assertThat(usuario.getDataNascimento()).isEqualTo(LocalDate.now().minusYears(20));
        assertThat(usuario.getEmail()).isEqualTo("jose.souza@email.com");
        assertThat(usuario.getPendente()).isFalse();
        assertThat(usuario.getBloqueado()).isFalse();
        assertThat(usuario.getSenha().getValor()).isNotNull();
        assertThat(usuario.getSenha().getResetToken()).isNull();
        assertThat(usuario.getAtivacaoToken()).isNull();
        assertAuditingFields(usuario, MOCK_EMAIL_ADMIN);
    }

    @Test
    public void testSave_whenHasPermissaoRoot() {
        Usuario usuario = UsuarioTestUtils.createForSaveService("Miguel Lima", "miguel.lima@email.com", findPermissoesByIds(Permissao.ID_ROOT));

        assertThrows(BusinessException.class, () -> usuarioService.save(usuario), "usuario.save.permissoes.root");
    }

    @Test
    public void testSave_whenHasPermissaoSystem() {
        Usuario usuario = UsuarioTestUtils.createForSaveService("Miguel Lima", "miguel.lima@email.com", findPermissoesByIds(Permissao.ID_SYSTEM));

        assertThrows(BusinessException.class, () -> usuarioService.save(usuario), "usuario.save.permissoes.system");
    }

    @Test
    public void testSave_whenHasPermissaoAdmin() {
        Usuario usuario = UsuarioTestUtils.createForSaveService("Miguel Lima", "miguel.lima@email.com", findPermissoesByIds(Permissao.ID_ADMIN));
        usuarioService.save(usuario);

        verifySendMail(1);
        assertThat(usuario.getId()).isNotNull();
        assertThat(usuario.anyPermissaoMatch(Permissao::isAdmin)).isTrue();
    }

    @Test
    public void testSave_whenHasPermissaoAdminAndAdminOrRootNotAuthenticated() {
        mockAuthenticationForAuditing("system@email.com");
        Usuario usuario = UsuarioTestUtils.createForSaveService("Miguel Lima", "miguel.lima@email.com", findPermissoesByIds(Permissao.ID_ADMIN));

        assertThrows(BusinessException.class, () -> usuarioService.save(usuario), "usuario.save.permissoes.admin");
    }

    @Test
    public void testSave_whenEmailNotUnique() {
        Usuario usuario1 = UsuarioTestUtils.create("Miguel Lima", "miguel.lima@email.com");
        usuarioRepository.save(usuario1);

        Usuario usuario2 = UsuarioTestUtils.createForSaveService("Miguel Araújo Lima", "miguel.lima@email.com");

        assertThrows(DuplicateKeyException.class, () -> usuarioService.save(usuario2), "usuario.duplicate-key.email");
    }

    @Test
    public void testUpdate() {
        Usuario usuario = UsuarioTestUtils.create("Miguel Lima", "miguel.lima@email.com");
        usuarioRepository.save(usuario);

        Usuario usuarioModificado = UsuarioTestUtils.createForSaveService("Miguel Borba Lima", "miguel.lima@email.com");
        Usuario usuarioAtualizado = usuarioService.update(usuario.getId(), usuarioModificado);

        assertThat(usuarioAtualizado.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioAtualizado.getNome()).isEqualTo("Miguel Borba Lima");
    }

    @Test
    public void testUpdate_whenRootPermissaoAlterada() {
        Usuario usuario = UsuarioTestUtils.create("Super User", "root@email.com", findPermissoesByIds(Permissao.ID_ADMIN));

        assertThrows(BusinessException.class, () -> usuarioService.update(Usuario.ID_ROOT, usuario), "usuario.update.permissoes.root");
    }

    @Test
    public void testUpdate_whenSystemPermissaoAlterada() {
        Usuario usuario = UsuarioTestUtils.create("System User", "system@email.com", findPermissoesByIds(Permissao.ID_ADMIN));

        assertThrows(BusinessException.class, () -> usuarioService.update(Usuario.ID_SYSTEM, usuario), "usuario.update.permissoes.system");
    }

    @Test
    public void testUpdate_whenAdminPermissaoAlterada() {
        Usuario usuario = UsuarioTestUtils.create("Super User", "root@email.com", findPermissoesByIds(Permissao.ID_SYSTEM));

        assertThrows(BusinessException.class, () -> usuarioService.update(Usuario.ID_ADMIN, usuario), "usuario.update.permissoes.admin");
    }

    @Test
    public void testUpdateAutenticado() {
        Usuario usuario = usuarioRepository.save(UsuarioTestUtils.create("Miguel Lima", "miguel.lima@email.com"));
        mockAuthenticationForAuditing("miguel.lima@email.com");

        Usuario usuarioAtualizado = usuarioService.updateAutenticado(new UsuarioBuilder()
                .withNome("Miguel Borba Lima")
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail("miguel.lima@email.com")
                .build());

        assertThat(usuario.getId()).isEqualTo(usuarioAtualizado.getId());
        assertThat(usuarioAtualizado.getNome()).isEqualTo("Miguel Borba Lima");
    }

    @Test
    public void testUpdateSenhaByResetToken() {
        Usuario usuario = usuarioRepository.save(UsuarioTestUtils.createBloqueado("Manoel Alves", "manoel.alves@email.com"));

        String valorSenha = (UsuarioTestUtils.MOCK_SENHA_PREFIX + "manoel.alves@email.com").substring(0, 30);
        Usuario usuarioAtualizado = usuarioService.updateSenhaByResetToken(usuario.getSenha().getResetToken(), valorSenha);

        assertThat(BcryptUtils.validate(valorSenha, usuarioAtualizado.getSenha().getValor())).isTrue();
        assertThat(usuarioAtualizado.getSenha().getResetToken()).isNull();
    }

    @Test
    public void testUpdateSenhaByResetToken_whenSenhaIsNotValid() {
        Usuario usuario = usuarioRepository.save(UsuarioTestUtils.createBloqueado("Manoel Alves", "manoel.alves@email.com"));
        
        assertThrows(InvalidPasswordException.class,
                () -> usuarioService.updateSenhaByResetToken(usuario.getSenha().getResetToken(), "manoel.alves"));
    }

    @Test
    public void testUpdateSenhaAutenticado() {
        usuarioRepository.save(UsuarioTestUtils.create("Alice Lima", "alice.lima@email.com"));
        mockAuthenticationForAuditing("alice.lima@email.com");

        Usuario usuarioAtualizado = usuarioService.updateSenhaAutenticado(UsuarioTestUtils.MOCK_SENHA_PREFIX + "alice.lima@email.com",
                "Alice.Lima@987");

        assertThat(BcryptUtils.validate("Alice.Lima@987", usuarioAtualizado.getSenha().getValor()));
    }

    @Test
    public void testUpdateSenhaAutenticado_whenInvalidActualPassword() {
        usuarioRepository.save(UsuarioTestUtils.create("Alice Lima", "alice.lima@email.com"));
        mockAuthenticationForAuditing("alice.lima@email.com");

        assertThrows(InvalidActualPasswordException.class,
                () -> usuarioService.updateSenhaAutenticado(UsuarioTestUtils.MOCK_SENHA_PREFIX + "alicelima@email.com", "Alice.Lima@987"));
    }

    @Test
    public void testUpdateSenhaAutenticado_whenInvalidPassword() {
        usuarioRepository.save(UsuarioTestUtils.create("Alice Lima", "alice.lima@email.com"));
        mockAuthenticationForAuditing("alice.lima@email.com");
        
        assertThrows(InvalidPasswordException.class,
                () -> usuarioService.updateSenhaAutenticado(UsuarioTestUtils.MOCK_SENHA_PREFIX + "alice.lima@email.com", "alice.lima"));
    }

    @Test
    public void testRecoverSenha() {
        usuarioRepository.save(UsuarioTestUtils.create("Alice Lima", "alice.lima@email.com"));

        String email = "alice.lima@email.com";
        usuarioService.recoverSenha(email);
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).get();

        verifySendMail(1);
        assertThat(usuario.getSenha().getResetToken()).isNotNull();
    }

    private void assertIsAdmin(Usuario usuario) {
        assertThat(usuario.getId()).isEqualTo(Usuario.ID_ADMIN);
    }

    private void verifySendMail(int times) {
        verify(mailService, times(times)).send(any(MailRequestTO.class));
    }

    private Permissao[] findPermissoesByIds(Long... ids) {
        return permissaoRepository.findAllById(List.of(ids)).toArray(Permissao[]::new);
    }

}
