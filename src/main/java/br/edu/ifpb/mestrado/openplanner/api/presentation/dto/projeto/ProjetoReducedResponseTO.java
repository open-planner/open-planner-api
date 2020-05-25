package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagResponseTO;

public class ProjetoReducedResponseTO implements Serializable {

    private static final long serialVersionUID = 4077046499585857067L;

    private Long id;

    private PeriodoResponseTO periodo;

    private String descricao;

    @JsonProperty("prioridade")
    private String prioridadeLabel;

    @JsonProperty("status")
    private String statusLabel;

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

    public String getPrioridadeLabel() {
        return prioridadeLabel;
    }

    public void setPrioridadeLabel(String prioridadeLabel) {
        this.prioridadeLabel = prioridadeLabel;
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
