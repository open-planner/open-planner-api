package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.Status;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoResponseTO;

public class PlanoFeriasRequestTO implements Serializable {

    private static final long serialVersionUID = 5916096502268452705L;

    @Valid
    private PeriodoResponseTO periodo;

    @NotBlank
    @Size(min = 3, max = 100)
    private String destino;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String anotacoes;

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

}
