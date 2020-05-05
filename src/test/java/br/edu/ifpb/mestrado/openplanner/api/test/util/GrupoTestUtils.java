package br.edu.ifpb.mestrado.openplanner.api.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.grupo.Grupo;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.grupo.GrupoResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.GrupoBuilder;

public class GrupoTestUtils {

    public static void assertResponseTO(GrupoResponseTO grupoResponseTO, Grupo grupo) {
        assertThat(grupoResponseTO.getId()).isEqualTo(grupo.getId());
        assertThat(grupoResponseTO.getNome()).isEqualTo(grupo.getNome());
        assertThat(grupoResponseTO.getAtivo()).isEqualTo(grupo.getAtivo());
        assertThat(grupoResponseTO.getLinks()).hasSize(3);

        grupoResponseTO.getPermissoes().stream()
                .forEach(permissaoResponseTO -> {
                    Optional<Permissao> permissaoOpt = grupo.getPermissoes().stream()
                            .filter(permissao -> permissao.getId().equals(permissaoResponseTO.getId()))
                            .findFirst();
                    PermissaoTestUtils.assertResponseTO(permissaoResponseTO, permissaoOpt.get());
                });
    }

    public static Grupo create(String nome, Boolean ativo, Set<Permissao> permissoes) {
        return new GrupoBuilder()
                .withNome(nome)
                .withPermissoes(permissoes)
                .withAtivo(ativo)
                .build();
    }

    public static Grupo create(Long id, String nome, Boolean ativo, Set<Permissao> permissoes) {
        return new GrupoBuilder()
                .withId(id)
                .withNome(nome)
                .withPermissoes(permissoes)
                .withAtivo(ativo)
                .build();
    }

    public static Grupo create(String nome, Boolean ativo) {
        return create(nome, ativo, null);
    }

    public static Grupo create(Long id, String nome, Boolean ativo) {
        return create(id, nome, ativo, null);
    }

    public static Grupo createAdminMock() {
        return create(1L, "Administrador", true, Set.of(PermissaoTestUtils.createAdminMock()));
    }

}
