package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto;

import java.io.Serializable;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Prioridade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.converter.IdReference;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoRequestTO;

public class ProjetoRequestTO implements Serializable {

    private static final long serialVersionUID = 4077046499585857067L;

    private PeriodoRequestTO periodo;

    private String descricao;

    private Prioridade prioridade;

    private Status status;

    private String anotacoes;

    private Set<NotificacaoRequestTO> notificacoes;

    @IdReference(property = "tags", target = Tag.class)
    private Set<Long> tags;

    public PeriodoRequestTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoRequestTO periodo) {
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
