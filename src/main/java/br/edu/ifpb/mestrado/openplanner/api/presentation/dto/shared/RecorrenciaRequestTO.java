package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class RecorrenciaRequestTO implements Serializable {

    private static final long serialVersionUID = -8612528713985828638L;

    @NotNull
    private RecorrenciaTimeUnit tipo;

    @NotNull
    private LocalDate dataLimite;

    public RecorrenciaTimeUnit getTipo() {
        return tipo;
    }

    public void setTipo(RecorrenciaTimeUnit tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

}
