package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Periodo implements Serializable {

    private static final long serialVersionUID = -1320538259428103809L;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return String.format("Periodo [dataInicio=%s, dataFim=%s]", dataInicio, dataFim);
    }

}
