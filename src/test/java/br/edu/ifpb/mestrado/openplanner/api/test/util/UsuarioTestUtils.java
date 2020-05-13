package br.edu.ifpb.mestrado.openplanner.api.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Senha;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.util.BcryptUtils;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuarioautenticado.UsuarioAutenticadoResponseTO;
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
        assertThat(usuarioResponseTO.getLinks()).hasSize(2);

        usuarioResponseTO.getPermissoes().stream()
                .forEach(permissaoResponseTO -> {
                    Optional<Permissao> permissaoOpt = usuario.getPermissoes().stream()
                            .filter(permissao -> permissao.getId().equals(permissaoResponseTO.getId()))
                            .findFirst();
                    PermissaoTestUtils.assertResponseTO(permissaoResponseTO, permissaoOpt.get());
                });
    }

    public static void assertResponseTO(UsuarioAutenticadoResponseTO usuarioResponseTO, Usuario usuario) {
        assertThat(usuarioResponseTO.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioResponseTO.getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioResponseTO.getDataNascimento()).isEqualTo(usuario.getDataNascimento());
        assertThat(usuarioResponseTO.getEmail()).isEqualTo(usuario.getEmail());
        assertThat(usuarioResponseTO.getLinks()).hasSize(2);
    }

    public static Usuario create(String nome, String email, Permissao... permissoes) {
        String valorSenha = MOCK_SENHA_PREFIX + (email.length() > 21 ? email.substring(0, 21) : email);

        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withPendente(false)
                .withBloqueado(false)
                .withSenha(new Senha(BcryptUtils.encode(valorSenha), null))
                .withPermissoes(new HashSet<>(Arrays.asList(permissoes)))
                .build();
    }

    public static Usuario createForSaveService(String nome, String email, Permissao... permissoes) {
        String valorSenha = MOCK_SENHA_PREFIX + (email.length() > 21 ? email.substring(0, 21) : email);

        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withSenha(new Senha(valorSenha, null))
                .withPermissoes(new HashSet<>(Arrays.asList(permissoes)))
                .build();
    }

    public static Usuario createPendente(String nome, String email, Permissao... permissoes) {
        String valorSenha = MOCK_SENHA_PREFIX + (email.length() > 21 ? email.substring(0, 21) : email);

        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withPendente(true)
                .withBloqueado(false)
                .withSenha(new Senha(BcryptUtils.encode(valorSenha), null))
                .withPermissoes(new HashSet<>(Arrays.asList(permissoes)))
                .withAtivacaoToken(MOCK_TOKEN_PREFIX + email)
                .build();
    }

    public static Usuario createBloqueado(String nome, String email, Permissao... permissoes) {
        return new UsuarioBuilder()
                .withNome(nome)
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(email)
                .withPendente(false)
                .withBloqueado(true)
                .withSenha(new Senha(null, MOCK_TOKEN_PREFIX + email))
                .withPermissoes(new HashSet<>(Arrays.asList(permissoes)))
                .build();
    }

    public static Usuario createAdminMock() {
        return new UsuarioBuilder()
                .withId(Usuario.ID_ADMIN)
                .withNome("Administrador")
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail("admin@email.com")
                .withPendente(false)
                .withBloqueado(false)
                .withSenha(new Senha(MOCK_SENHA_PREFIX + "admin@email.com"))
                .withPermissoes(new HashSet<>(Arrays.asList(PermissaoTestUtils.createAdminMock())))
                .build();
    }

}
