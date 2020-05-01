package br.edu.ifpb.mestrado.openplanner.api.application.service;

import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.assertAuditingFields;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.assertPage;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.assertPageNoContent;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.createSpecification;
import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.mockAuthenticationForAuditing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.BusinessException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.grupo.Grupo;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.GrupoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.GrupoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.PermissaoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.BaseEntitySpecification;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.grupo.GrupoFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.GrupoFilterRequestTOBuilder;
import br.edu.ifpb.mestrado.openplanner.api.test.util.GrupoTestUtils;

@ActiveProfiles("test")
@SpringBootTest
public class GrupoServiceImplTest {

    private static final String MOCK_EMAIL_ADMIN = "admin@email.com";
    private static final String MOCK_EMAIL_ROOT = "root@email.com";

    private GrupoService grupoService;

    private GrupoRepository grupoRepository;

    private PermissaoRepository permissaoRepository;

    @Autowired
    public GrupoServiceImplTest(GrupoService grupoService, GrupoRepository grupoRepository, PermissaoRepository permissaoRepository) {
        this.grupoService = grupoService;
        this.grupoRepository = grupoRepository;
        this.permissaoRepository = permissaoRepository;
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockAuthenticationForAuditing(MOCK_EMAIL_ADMIN);

        grupoRepository.save(GrupoTestUtils.createGrupo("Gerência", true, findPermissoesByIds(1L)));
        grupoRepository.save(GrupoTestUtils.createGrupo("Recepção", true, findPermissoesByIds(2L, 3L, 4L)));
        grupoRepository.save(GrupoTestUtils.createGrupo("Recursos Humanos", true, findPermissoesByIds(2L, 3L, 4L, 5L)));
        grupoRepository.save(GrupoTestUtils.createGrupo("Vendas", false));
    }

    @AfterEach
    public void tearDown() throws Exception {
        Specification<Grupo> specification = BaseEntitySpecification.idGreaterThan(Grupo.ID_ADMIN);
        grupoRepository.deleteAll(grupoRepository.findAll(specification));
    }

    @Test
    public void testFindById() {
        Grupo grupo = grupoService.findById(Grupo.ID_ADMIN);

        assertIsAdmin(grupo);
    }

    @Test
    public void testFindById_whenNotFound() {
        assertThrows(InformationNotFoundException.class, () -> grupoService.findById(99L));
    }

    @Test
    public void testFindAllByPageable() {
        Page<Grupo> gruposPage = grupoService.findAll(PageRequest.of(0, 10));

        assertPage(gruposPage, 10, 0, 5, 1, 5);
    }

    @Test
    public void testFindAllByPageable_whenRoot() {
        mockAuthenticationForAuditing(MOCK_EMAIL_ROOT);
        Page<Grupo> gruposPage = grupoService.findAll(PageRequest.of(0, 10));
        
        assertPage(gruposPage, 10, 0, 7, 1, 7);
    }

    @Test
    public void testFindAllBySpecificationAndPageable_filterById() {
        GrupoFilterRequestTO filter = new GrupoFilterRequestTOBuilder()
                .withId(Grupo.ID_ADMIN)
                .build();

        Page<Grupo> gruposPage = grupoService.findAll(createSpecification(filter, Grupo.class), PageRequest.of(0, 10));

        assertPage(gruposPage, 10, 0, 1, 1, 1);
        assertIsAdmin(gruposPage.getContent().get(0));
    }

    @Test
    public void testFindAllBySpecificationAndPageable_filterByNome() {
        GrupoFilterRequestTO filter = new GrupoFilterRequestTOBuilder()
                .withNome("recepcao")
                .build();

        Page<Grupo> gruposPage = grupoService.findAll(createSpecification(filter, Grupo.class), PageRequest.of(0, 10));

        assertPage(gruposPage, 10, 0, 1, 1, 1);
        assertThat(gruposPage.getContent().get(0).getNome()).isEqualTo("Recepção");
    }

    @Test
    public void testFindAllBySpecificationAndPageable_filterByAtivo() {
        GrupoFilterRequestTO filter = new GrupoFilterRequestTOBuilder()
                .withAtivo(true)
                .build();

        Page<Grupo> gruposPage = grupoService.findAll(createSpecification(filter, Grupo.class), PageRequest.of(0, 10));

        assertPage(gruposPage, 10, 0, 4, 1, 4);
        assertThat(gruposPage.getContent().stream()
                .anyMatch(g -> !g.getAtivo())).isFalse();
    }

    @Test
    public void testFindAllBySpecificationAndPageable_notFound() {
        GrupoFilterRequestTO filter = new GrupoFilterRequestTOBuilder()
                .withNome("tecnologia da informacao")
                .build();

        Page<Grupo> gruposPage = grupoService.findAll(createSpecification(filter, Grupo.class), PageRequest.of(0, 10));

        assertPageNoContent(gruposPage, 10, 0);
    }

    @Test
    public void testFindAllActive() {
        List<Grupo> grupos = grupoService.findAllActive();

        assertThat(grupos).hasSize(4);
    }

    @Test
    public void testSave() {
        Grupo grupo = GrupoTestUtils.createGrupo("Marketing", true, findPermissoesByIds(Permissao.ID_ADMIN));
        grupoService.save(grupo);

        assertThat(grupo.getId()).isGreaterThan(1L);
        assertThat(grupo.getNome()).isEqualTo("Marketing");
        assertThat(grupo.getPermissoes()).hasSize(1);
        assertThat(grupo.getAtivo()).isTrue();
        assertAuditingFields(grupo, MOCK_EMAIL_ADMIN);
    }

    @Test
    public void testSave_whenHasPermissaoRoot() {
        Grupo grupo = GrupoTestUtils.createGrupo("Marketing", true, findPermissoesByIds(Permissao.ID_ROOT));

        assertThrows(BusinessException.class, () -> grupoService.save(grupo), "grupo.save.permissoes.root");
    }

    @Test
    public void testSave_whenHasPermissaoSystem() {
        Grupo grupo = GrupoTestUtils.createGrupo("Marketing", true, findPermissoesByIds(Permissao.ID_SYSTEM));

        assertThrows(BusinessException.class, () -> grupoService.save(grupo), "grupo.save.permissoes.system");
    }

    @Test
    public void testSave_whenHasPermissaoAdmin() {
        Grupo grupo = grupoService.save(GrupoTestUtils.createGrupo("Marketing", true, findPermissoesByIds(Permissao.ID_ADMIN)));

        assertThat(grupo.getId()).isGreaterThan(1L);
        assertThat(grupo.getNome()).isEqualTo("Marketing");
        assertThat(grupo.getPermissoes()).hasSize(1);
        assertThat(grupo.getAtivo()).isTrue();
        assertAuditingFields(grupo, MOCK_EMAIL_ADMIN);
    }

    @Test
    public void testSave_whenHasPermissaoAdminAndAdminOrRootNotAuthenticated() {
        mockAuthenticationForAuditing("system");

        Grupo grupo = GrupoTestUtils.createGrupo("Marketing", true, findPermissoesByIds(Permissao.ID_SYSTEM));

        assertThrows(BusinessException.class, () -> grupoService.save(grupo), "grupo.save.permissoes.admin");
    }

    @Test
    public void testUpdate() {
        Long idGrupo = grupoRepository.save(GrupoTestUtils.createGrupo("Marketing", false)).getId();

        Grupo grupo = grupoService.update(idGrupo, GrupoTestUtils.createGrupo("Marketing", true, findPermissoesByIds(Permissao.ID_ADMIN)));

        assertThat(grupo.getId()).isEqualTo(idGrupo);
        assertThat(grupo.getNome()).isEqualTo("Marketing");
        assertThat(grupo.getPermissoes()).hasSize(1);
        assertThat(grupo.getAtivo()).isTrue();
        assertAuditingFields(grupo, MOCK_EMAIL_ADMIN);
    }

    @Test
    public void testUpdate_whenNotFound() {
        Grupo grupo = GrupoTestUtils.createGrupo("Marketing", true, findPermissoesByIds(4L));

        assertThrows(InformationNotFoundException.class, () -> grupoService.update(99L, grupo));
    }

    @Test
    public void testUpdate_whenRootPermissaoAlterada() {
        Grupo grupo = GrupoTestUtils.createGrupo("Super User", true, findPermissoesByIds(1L));

        assertThrows(BusinessException.class, () -> grupoService.update(Grupo.ID_ROOT, grupo), "grupo.update.root.permissoes");
    }

    @Test
    public void testUpdate_whenSystemPermissaoAlterada() {
        Grupo grupo = GrupoTestUtils.createGrupo("System User", true, findPermissoesByIds(1L));

        assertThrows(BusinessException.class, () -> grupoService.update(Grupo.ID_SYSTEM, grupo), "grupo.update.system.permissoes");
    }

    @Test
    public void testUpdate_whenAdminPermissaoAlterada() {
        Grupo grupo = GrupoTestUtils.createGrupo("Admin User", true, findPermissoesByIds(2L));

        assertThrows(BusinessException.class, () -> grupoService.update(Grupo.ID_ADMIN, grupo), "grupo.update.admin.permissoes");
    }

    @Test
    public void testSwitchActive() {
        Long idGrupo = grupoRepository.save(GrupoTestUtils.createGrupo("Marketing", false, findPermissoesByIds(Permissao.ID_ADMIN)))
                .getId();

        Grupo grupo = grupoService.switchActive(idGrupo);

        assertThat(grupo.getId()).isEqualTo(idGrupo);
        assertThat(grupo.getNome()).isEqualTo("Marketing");
        assertThat(grupo.getPermissoes()).hasSize(1);
        assertThat(grupo.getAtivo()).isTrue();
        assertAuditingFields(grupo, MOCK_EMAIL_ADMIN);
    }

    @Test
    public void testSwitchActive_whenNotFound() {
        assertThrows(InformationNotFoundException.class, () -> grupoService.switchActive(99L));
    }

    private void assertIsAdmin(Grupo grupo) {
        assertThat(grupo.getId()).isEqualTo(Grupo.ID_ADMIN);
    }

    private Set<Permissao> findPermissoesByIds(Long... ids) {
        return new HashSet<>(permissaoRepository.findAllById(List.of(ids)));
    }

}
