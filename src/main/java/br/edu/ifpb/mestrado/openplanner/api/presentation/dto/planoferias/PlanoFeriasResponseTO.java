package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias;

import java.io.Serializable;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.Status;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoResponseTO;

public class PlanoFeriasResponseTO implements Serializable {

    private static final long serialVersionUID = 5916096502268452705L;

    private Long id;

    private PeriodoResponseTO periodo;

    private String destino;

    private Status status;

    private String anotacoes;

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
