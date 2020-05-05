package br.edu.ifpb.mestrado.openplanner.api.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.grupo.Grupo;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Senha;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.util.BcryptUtils;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.UsuarioBuilder;

public class UsuarioTestUtils {

    public static final String MOCK_SENHA_PREFIX = "Senha123#";
    public static final String MOCK_TOKEN_PREFIX = "Token123#";

    public static void assertResponseTO(UsuarioResponseTO usuarioResponseTO, Usuario usuario) {
        assertThat(usuarioResponseTO.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioResponseTO.getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioResponseTO.getDataNascimento()).isEqualTo(usuario.getDataNascimento());
        assertThat(usuarioResponseTO.getEmail()).isEqualTo(usuario.getEmail());
        assertThat(usuarioResponseTO.getPendente()).isEqualTo(usuario.getPendente());
        assertThat(usuarioResponseTO.getBloqueado()).isEqualTo(usuario.getBloqueado());
        assertThat(usuarioResponseTO.getAtivo()).isEqualTo(usuario.getAtivo());
        assertThat(usuarioResponseTO.getLinks()).hasSize(3);

        usuarioResponseTO.getGrupos().stream()
                .forEach(grupoResponseTO -> {
                    Optional<Grupo> grupoOpt = usuario.getGrupos().stream()
                            .filter(permissao -> permissao.getId().equals(grupoResponseTO.getId()))
                            .findFirst();
                    GrupoTestUtils.assertResponseTO(grupoResponseTO, grupoOpt.get());
                });
    }

    public static Usuario createForSaveService(String nome, String email, Set<Grupo> grupos) {
        String valorSenha = MOCK_SENHA_PREFIX + (email.length() > 21 ? email.substring(0, 21) : email);

        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withSenha(new Senha(valorSenha, null))
                .withGrupos(grupos)
                .build();
    }

    public static Usuario createPendente(String nome, String email, Set<Grupo> grupos) {
        String valorSenha = MOCK_SENHA_PREFIX + (email.length() > 21 ? email.substring(0, 21) : email);

        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withAtivo(false)
                .withPendente(true)
                .withBloqueado(false)
                .withSenha(new Senha(BcryptUtils.encode(valorSenha), null))
                .withGrupos(grupos)
                .withAtivacaoToken(MOCK_TOKEN_PREFIX + email)
                .build();
    }

    public static Usuario createAtivo(String nome, String email, Set<Grupo> grupos) {
        String valorSenha = MOCK_SENHA_PREFIX + (email.length() > 21 ? email.substring(0, 21) : email);

        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withAtivo(true)
                .withPendente(false)
                .withBloqueado(false)
                .withSenha(new Senha(BcryptUtils.encode(valorSenha), null))
                .withGrupos(grupos)
                .build();
    }

    public static Usuario createInativo(String nome, String email, Set<Grupo> grupos) {
        String valorSenha = MOCK_SENHA_PREFIX + (email.length() > 21 ? email.substring(0, 21) : email);

        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withAtivo(false)
                .withPendente(false)
                .withBloqueado(false)
                .withSenha(new Senha(BcryptUtils.encode(valorSenha), null))
                .withGrupos(grupos)
                .build();
    }

    public static Usuario createBloqueado(String nome, String email, Set<Grupo> grupos) {
        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withAtivo(true)
                .withPendente(false)
                .withBloqueado(true)
                .withSenha(new Senha(null, MOCK_TOKEN_PREFIX + email))
                .withGrupos(grupos)
                .build();
    }

    public static Usuario createAdminMock() {
        return new UsuarioBuilder()
                .withId(Usuario.ID_ADMIN)
                .withNome("Administrador")
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail("admin@email.com")
                .withAtivo(true)
                .withPendente(false)
                .withBloqueado(false)
                .withSenha(new Senha(MOCK_SENHA_PREFIX + "admin@email.com"))
                .withGrupos(Set.of(GrupoTestUtils.createAdminMock()))
                .build();
    }

}
