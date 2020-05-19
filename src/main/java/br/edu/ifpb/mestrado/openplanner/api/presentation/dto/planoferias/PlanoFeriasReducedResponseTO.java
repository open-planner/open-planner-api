package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoResponseTO;

public class PlanoFeriasReducedResponseTO implements Serializable {

    private static final long serialVersionUID = 5916096502268452705L;

    private Long id;

    private PeriodoResponseTO periodo;

    private String destino;

    @JsonProperty("status")
    private String statusLabel;

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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

}
