package br.edu.ifpb.mestrado.openplanner.api.test.builder;

import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.grupo.Grupo;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;

public class GrupoBuilder {

    private Grupo grupo = new Grupo();

    public GrupoBuilder withId(Long id) {
        grupo.setId(id);
        return this;
    }

    public GrupoBuilder withNome(String nome) {
        grupo.setNome(nome);
        return this;
    }

    public GrupoBuilder withPermissoes(Set<Permissao> permissoes) {
        grupo.setPermissoes(permissoes);
        return this;
    }

    public GrupoBuilder withAtivo(Boolean ativo) {
        grupo.setAtivo(ativo);
        return this;
    }

    public Grupo build() {
        return grupo;
    }

}
