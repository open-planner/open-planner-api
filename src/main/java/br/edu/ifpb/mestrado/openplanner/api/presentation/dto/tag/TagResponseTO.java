package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag;

import java.io.Serializable;

public class TagResponseTO implements Serializable {

    private static final long serialVersionUID = -8038116913424450605L;

    private Long id;

    private String descricao;

    private String cor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return String.format("TagResponseTO [id=%s, descricao=%s, cor=%s]", id, descricao, cor);
    }

}
