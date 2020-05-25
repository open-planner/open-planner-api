package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.meta.Status;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoJoinResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagResponseTO;

public class MetaResponseTO implements Serializable {

    private static final long serialVersionUID = 2473003280630519076L;

    private Long id;

    private LocalDate data;

    private String descricao;

    private Status status;

    private String anotacoes;

    private Set<NotificacaoJoinResponseTO> notificacoes;

    private Set<TagResponseTO> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<NotificacaoJoinResponseTO> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<NotificacaoJoinResponseTO> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public Set<TagResponseTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagResponseTO> tags) {
        this.tags = tags;
    }

}
