package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Tipo;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoRequestTO;

public class ViagemRequestTO implements Serializable {

    private static final long serialVersionUID = -3547717878121719742L;

    @NotNull
    private Tipo tipo;

    @Valid
    private PeriodoRequestTO periodo;

    @NotBlank
    @Size(min = 3, max = 100)
    private String destino;

    @NotNull
    private Status status;

    private String anotacoes;

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public PeriodoRequestTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoRequestTO periodo) {
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
