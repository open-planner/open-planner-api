package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.grupo;

import java.io.Serializable;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.specification.SpecificationField;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;

public class GrupoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 8652626915074598836L;

    private Long id;

    private Set<Long> ids;

    @SpecificationField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
    private String nome;

    private Boolean ativo;

    public GrupoFilterRequestTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return String.format("GrupoFilterRequestTO [id=%s, ids=%s, nome=%s, ativo=%s]", id, ids, nome, ativo);
    }

}
