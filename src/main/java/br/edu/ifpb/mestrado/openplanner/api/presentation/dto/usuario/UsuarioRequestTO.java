package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.converter.IdReference;

public class UsuarioRequestTO implements Serializable {

    private static final long serialVersionUID = -7433561693465074511L;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    @IdReference(target = Permissao.class, property = "permissoes")
    private Set<Long> permissoes;

    public UsuarioRequestTO() {
        super();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Long> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Long> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public String toString() {
        return String.format("UsuarioRequestTO [nome=%s, dataNascimento=%s, email=%s, permissoes=%s]", nome, dataNascimento, email,
                permissoes);
    }

}
