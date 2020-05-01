package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.grupo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

public class GrupoReducedResponseTO extends RepresentationModel<GrupoReducedResponseTO> implements Serializable {

    private static final long serialVersionUID = -9206313086321180556L;

    private Long id;

    private String nome;

    private Boolean ativo;

    public GrupoReducedResponseTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return String.format("GrupoReducedResponseTO [id=%s, nome=%s, ativo=%s]", id, nome, ativo);
    }
}
