package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa.Status;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.converter.IdReference;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.recorrencia.RecorrenciaRequestTO;

public class TarefaRequestTO implements Serializable {

    private static final long serialVersionUID = 8217482941557373012L;

    @NotNull
    private LocalDateTime dataHora;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    @NotNull
    private Status status;

    private String anotacoes;

    @IdReference(property = "tags", target = Tag.class)
    private Set<Long> tags;

    @Valid
    private Set<NotificacaoRequestTO> notificacoes;

    @Valid
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

    public Set<Long> getTags() {
        return tags;
    }

    public void setTags(Set<Long> tags) {
        this.tags = tags;
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
