package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagResponseTO;

public class TarefaReducedResponseTO implements Serializable {

    private static final long serialVersionUID = 2782903388711202046L;

    private Long id;

    private LocalDateTime dataHora;

    private String descricao;

    @JsonProperty("status")
    private String statusLabel;

    private Set<TagResponseTO> tags;

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

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Set<TagResponseTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagResponseTO> tags) {
        this.tags = tags;
    }

}
