package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

public class UsuarioReducedResponseTO extends RepresentationModel<UsuarioReducedResponseTO> implements Serializable {

    private static final long serialVersionUID = 1113904085583736516L;

    private Long id;

    private String nome;

    private String email;

    private Boolean pendente;

    private Boolean bloqueado;

    public UsuarioReducedResponseTO() {
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

    @Override
    public String toString() {
        return String.format("UsuarioReducedResponseTO [id=%s, nome=%s, email=%s, pendente=%s, bloqueado=%s]", id, nome, email, pendente,
                bloqueado);
    }

}
