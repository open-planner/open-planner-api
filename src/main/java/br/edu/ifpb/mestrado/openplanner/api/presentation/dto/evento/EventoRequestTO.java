package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.RecorrenciaRequestTO;

public class EventoRequestTO implements Serializable {

    private static final long serialVersionUID = 5177952186914054795L;

    @NotNull
    private LocalDate data;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    private Set<NotificacaoRequestTO> notificacoes;

    private RecorrenciaRequestTO recorrencia;

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
