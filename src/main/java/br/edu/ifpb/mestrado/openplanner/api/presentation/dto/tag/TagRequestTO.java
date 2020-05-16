package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TagRequestTO implements Serializable {

    private static final long serialVersionUID = -8258221579687565611L;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    @Size(min = 3, max = 6)
    private String cor;

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
        return String.format("TagRequestTO [descricao=%s, cor=%s]", descricao, cor);
    }

}
