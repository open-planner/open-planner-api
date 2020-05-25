package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto;

import java.io.Serializable;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Prioridade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Status;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoJoinResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagResponseTO;

public class ProjetoResponseTO implements Serializable {

    private static final long serialVersionUID = 4077046499585857067L;

    private Long id;

    private PeriodoResponseTO periodo;

    private String descricao;

    private Prioridade prioridade;

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

    public PeriodoResponseTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoResponseTO periodo) {
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
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
