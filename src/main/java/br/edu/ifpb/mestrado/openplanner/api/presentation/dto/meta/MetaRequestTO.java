package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.meta.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.converter.IdReference;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoRequestTO;

public class MetaRequestTO implements Serializable {

    private static final long serialVersionUID = 4274896746833796526L;

    @NotNull
    private LocalDate data;

    @NotBlank
    @Size(max = 128)
    private String descricao;

    private Status status;

    private String anotacoes;

    private Set<NotificacaoRequestTO> notificacoes;

    @IdReference(property = "tags", target = Tag.class)
    private Set<Long> tags;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public Set<NotificacaoRequestTO> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<NotificacaoRequestTO> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public Set<Long> getTags() {
        return tags;
    }

    public void setTags(Set<Long> tags) {
        this.tags = tags;
    }

}
