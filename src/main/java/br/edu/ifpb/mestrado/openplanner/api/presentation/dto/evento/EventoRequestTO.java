package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.RecorrenciaRequestTO;

public class EventoRequestTO implements Serializable {

    private static final long serialVersionUID = 5177952186914054795L;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHora;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    @Valid
    private Set<NotificacaoRequestTO> notificacoes;

    private RecorrenciaRequestTO recorrencia;

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

    public Set<NotificacaoRequestTO> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<NotificacaoRequestTO> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public RecorrenciaRequestTO getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(RecorrenciaRequestTO recorrencia) {
        this.recorrencia = recorrencia;
    }

}
