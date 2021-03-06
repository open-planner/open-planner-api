package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.permissao.PermissaoResponseTO;

public class UsuarioResponseTO extends RepresentationModel<UsuarioResponseTO> implements Serializable {

    private static final long serialVersionUID = -2629152547405312072L;

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    private Boolean pendente;

    private Boolean bloqueado;

    private Set<PermissaoResponseTO> permissoes;

    public UsuarioResponseTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getPendente() {
        return pendente;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Set<PermissaoResponseTO> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<PermissaoResponseTO> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public String toString() {
        return String.format("UsuarioResponseTO [id=%s, nome=%s, dataNascimento=%s, email=%s, pendente=%s, bloqueado=%s, permissoes=%s]",
                id, nome, dataNascimento, email, pendente, bloqueado, permissoes);
    }

}
