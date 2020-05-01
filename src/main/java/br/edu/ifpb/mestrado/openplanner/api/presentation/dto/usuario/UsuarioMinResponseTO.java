package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

public class UsuarioMinResponseTO extends RepresentationModel<UsuarioMinResponseTO> implements Serializable {

    private static final long serialVersionUID = 2300401915261446061L;

    private Long id;

    private String nome;

    private String login;

    public UsuarioMinResponseTO() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return String.format("UsuarioMinResponseTO [id=%s, nome=%s, login=%s]", id, nome, login);
    }

}
