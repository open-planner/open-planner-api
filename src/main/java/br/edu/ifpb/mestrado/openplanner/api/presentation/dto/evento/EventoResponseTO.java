package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.recorrencia.RecorrenciaResponseTO;

public class EventoResponseTO implements Serializable {

    private static final long serialVersionUID = -2890983806375630404L;

    private Long id;

    private LocalDateTime dataHora;

    private String descricao;

    private Set<NotificacaoResponseTO> notificacoes;

    private RecorrenciaResponseTO recorrencia;

    private EventoReducedResponseTO relacao;

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

    public Set<NotificacaoResponseTO> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<NotificacaoResponseTO> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public RecorrenciaResponseTO getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(RecorrenciaResponseTO recorrencia) {
        this.recorrencia = recorrencia;
    }

    public EventoReducedResponseTO getRelacao() {
        return relacao;
    }

    public void setRelacao(EventoReducedResponseTO relacao) {
        this.relacao = relacao;
    }

}
