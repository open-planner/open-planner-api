package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.recorrencia;

import java.io.Serializable;
import java.time.LocalDate;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.RecorrenciaTimeUnit;

public class RecorrenciaResponseTO implements Serializable {

    private static final long serialVersionUID = -361072754298615106L;

    private RecorrenciaTimeUnit unidade;

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
