package br.edu.ifpb.mestrado.openplanner.api.test.builder;

import java.time.LocalDate;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.grupo.Grupo;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Senha;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;

public class UsuarioBuilder {

    private Usuario usuario = new Usuario();

    public UsuarioBuilder withId(Long id) {
        usuario.setId(id);
        return this;
    }

    public UsuarioBuilder withNome(String nome) {
        usuario.setNome(nome);
        return this;
    }

    public UsuarioBuilder withDataNascimento(LocalDate dataNascimento) {
        usuario.setDataNascimento(dataNascimento);
        return this;
    }

    public UsuarioBuilder withEmail(String email) {
        usuario.setEmail(email);
        return this;
    }

    public UsuarioBuilder withSenha(Senha senha) {
        usuario.setSenha(senha);
        return this;
    }

    public UsuarioBuilder withPendente(Boolean pendente) {
        usuario.setPendente(pendente);
        return this;
    }

    public UsuarioBuilder withBloqueado(Boolean bloqueado) {
        usuario.setBloqueado(bloqueado);
        return this;
    }

    public UsuarioBuilder withAtivo(Boolean ativo) {
        usuario.setAtivo(ativo);
        return this;
    }

    public UsuarioBuilder withGrupos(Set<Grupo> grupos) {
        usuario.setGrupos(grupos);
        return this;
    }

    public UsuarioBuilder withAtivacaoToken(String ativacaoToken) {
        usuario.setAtivacaoToken(ativacaoToken);
        return this;
    }

    public Usuario build() {
        return usuario;
    }

}
