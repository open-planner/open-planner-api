package br.edu.ifpb.mestrado.openplanner.api.test.builder;

import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.grupo.GrupoFilterRequestTO;

public class GrupoFilterRequestTOBuilder {

    private GrupoFilterRequestTO grupoFilter = new GrupoFilterRequestTO();

    public GrupoFilterRequestTOBuilder withId(Long id) {
        grupoFilter.setId(id);
        return this;
    }

    public GrupoFilterRequestTOBuilder withIds(Set<Long> ids) {
        grupoFilter.setIds(ids);
        return this;
    }

    public GrupoFilterRequestTOBuilder withNome(String nome) {
        grupoFilter.setNome(nome);
        return this;
    }

    public GrupoFilterRequestTOBuilder withAtivo(Boolean ativo) {
        grupoFilter.setAtivo(ativo);
        return this;
    }

    public GrupoFilterRequestTO build() {
        return grupoFilter;
    }

}
