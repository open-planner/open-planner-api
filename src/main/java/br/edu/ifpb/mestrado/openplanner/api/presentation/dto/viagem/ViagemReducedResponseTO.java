package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ViagemReducedResponseTO implements Serializable {

    private static final long serialVersionUID = -7367903701277015283L;

    private Long id;

    @JsonProperty("tipo")
    private String tipoLabel;

    private LocalDate data;

    private String destino;

    @JsonProperty("status")
    private String statusLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoLabel() {
        return tipoLabel;
    }

    public void setTipoLabel(String tipoLabel) {
        this.tipoLabel = tipoLabel;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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
