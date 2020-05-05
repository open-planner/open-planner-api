package br.edu.ifpb.mestrado.openplanner.api.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Papel;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.permissao.PermissaoResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.PermissaoBuilder;

public class PermissaoTestUtils {

    public static void assertResponseTO(PermissaoResponseTO permissaoResponseTO, Permissao permissao) {
        assertThat(permissaoResponseTO.getId()).isEqualTo(permissao.getId());
        assertThat(permissaoResponseTO.getPapel()).isEqualTo(permissao.getPapel());
        assertThat(permissaoResponseTO.getDescricao()).isEqualTo(permissao.getDescricao());
        assertThat(permissaoResponseTO.getLinks()).isNullOrEmpty();
    }

    public static Permissao create(Papel papel, String descricao) {
        return new PermissaoBuilder()
                .withPapel(papel)
                .withDescricao(descricao)
                .build();
    }

    public static Permissao create(Long id, Papel papel, String descricao) {
        return new PermissaoBuilder()
                .withId(id)
                .withPapel(papel)
                .withDescricao(descricao)
                .build();
    }

    public static Permissao createAdminMock() {
        return create(Permissao.ID_ADMIN, Papel.ROLE_ADMIN, "Administrador");
    }

}
