package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag;

import java.io.Serializable;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;

@SpecEntity(Tag.class)
public class TagFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -7108575492995603675L;

    private Long id;

    @SpecField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
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

    @Override
    public String toString() {
        return String.format("TagFilterRequestTO [id=%s, descricao=%s]", id, descricao);
    }

}
