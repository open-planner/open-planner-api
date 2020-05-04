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

    private static final String MOCK_SENHA_PREFIX = "Senha123#";
    private static final String MOCK_TOKEN_PREFIX = "Token123#";

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

    public static Usuario createUsuarioPendente(String nome, LocalDate dataNascimento, String email, Set<Grupo> grupos) {
        return createUsuario(nome, dataNascimento, email, true, true, false, grupos);
    }

    public static Usuario createUsuarioAtivo(String nome, LocalDate dataNascimento, String email, Set<Grupo> grupos) {
        return createUsuario(nome, dataNascimento, email, true, false, false, grupos);
    }

    public static Usuario createUsuarioAtivo(Long id, String nome, LocalDate dataNascimento, String email, Set<Grupo> grupos) {
        return createUsuario(id, nome, dataNascimento, email, true, false, false, grupos);
    }

    public static Usuario createUsuarioInativo(String nome, LocalDate dataNascimento, String email, Set<Grupo> grupos) {
        return createUsuario(nome, dataNascimento, email, false, false, false, grupos);
    }

    public static Usuario createUsuarioBloqueado(String nome, LocalDate dataNascimento, String email, Set<Grupo> grupos) {
        return createUsuario(nome, dataNascimento, email, true, false, true, grupos);
    }

    public static Usuario createUsuario(Long id, String nome, LocalDate dataNascimento, String email, Boolean ativo, Boolean pendente,
            Boolean bloqueado, Set<Grupo> grupos) {
        String valorSenha = MOCK_SENHA_PREFIX + email;

        if (valorSenha.length() > 30) {
            valorSenha = valorSenha.substring(0, 30);
        }

        return new UsuarioBuilder()
                .withId(id)
                .withNome(nome)
                .withDataNascimento(dataNascimento)
                .withEmail(email)
                .withAtivo(ativo)
                .withPendente(pendente)
                .withBloqueado(bloqueado)
                .withSenha(new Senha(
                        !bloqueado ? BcryptUtils.encode(valorSenha) : null,
                        bloqueado ? MOCK_TOKEN_PREFIX + email : null))
                .withGrupos(grupos)
                .withAtivacaoToken(pendente ? MOCK_TOKEN_PREFIX + email : null)
                .build();
    }

    public static Usuario createUsuario(Long id, String nome, LocalDate dataNascimento, String email, Boolean ativo, Set<Grupo> grupos) {
        return createUsuario(id, nome, dataNascimento, email, ativo, false, false, grupos);
    }

    public static Usuario createUsuario(String nome, LocalDate dataNascimento, String email, Boolean ativo, Boolean pendente,
            Boolean bloqueado, Set<Grupo> grupos) {
        return createUsuario(null, nome, dataNascimento, email, ativo, pendente, bloqueado, grupos);
    }

    public static Usuario createUsuarioAdminMock() {
        return createUsuario(1L, "Administrador", LocalDate.now().minusYears(20), "admin@email.com", true, false, false,
                Set.of(GrupoTestUtils.createGrupoAdminMock()));
    }

}
