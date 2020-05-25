package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagResponseTO;

public class MetaReducedResponseTO implements Serializable {

    private static final long serialVersionUID = -8360615286785198875L;

    private Long id;

    private LocalDate data;

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
