package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa.Status;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoJoinResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.recorrencia.RecorrenciaResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagResponseTO;

public class TarefaResponseTO implements Serializable {

    private static final long serialVersionUID = -4998003823561759314L;

    private Long id;

    private LocalDateTime dataHora;

    private String descricao;

    private Status status;

    private String anotacoes;

    private Set<NotificacaoJoinResponseTO> notificacoes;

    private Set<TagResponseTO> tags;

    private RecorrenciaResponseTO recorrencia;

    private TarefaReducedResponseTO relacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
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

    public Set<TagResponseTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagResponseTO> tags) {
        this.tags = tags;
    }

    public Set<NotificacaoJoinResponseTO> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<NotificacaoJoinResponseTO> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public RecorrenciaResponseTO getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(RecorrenciaResponseTO recorrencia) {
        this.recorrencia = recorrencia;
    }

    public TarefaReducedResponseTO getRelacao() {
        return relacao;
    }

    public void setRelacao(TarefaReducedResponseTO relacao) {
        this.relacao = relacao;
    }

}
