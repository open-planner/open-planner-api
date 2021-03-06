package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag;

import java.io.Serializable;

import com.github.fagnerlima.springspecificationtools.SpecOperation;
import com.github.fagnerlima.springspecificationtools.annotation.SpecEntity;
import com.github.fagnerlima.springspecificationtools.annotation.SpecField;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;

@SpecEntity(Tag.class)
public class TagFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -7108575492995603675L;

    private Long id;

    @SpecField(operation = SpecOperation.LIKE_IGNORE_CASE_UNACCENT)
    private String descricao;

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

}
