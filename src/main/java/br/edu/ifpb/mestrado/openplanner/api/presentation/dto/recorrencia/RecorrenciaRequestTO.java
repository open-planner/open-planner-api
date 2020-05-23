package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.recorrencia;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.RecorrenciaTimeUnit;

public class RecorrenciaRequestTO implements Serializable {

    private static final long serialVersionUID = -8612528713985828638L;

    @NotNull
    private RecorrenciaTimeUnit unidade;

    @NotNull
    private LocalDate dataLimite;

    public RecorrenciaTimeUnit getUnidade() {
        return unidade;
    }

    public void setUnidade(RecorrenciaTimeUnit unidade) {
        this.unidade = unidade;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

}
