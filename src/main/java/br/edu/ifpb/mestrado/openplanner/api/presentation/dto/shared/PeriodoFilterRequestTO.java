package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecPeriodEndDate;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecPeriodStartDate;

public class PeriodoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 6907306552014617874L;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @SpecPeriodStartDate
    private LocalDate dataInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @SpecPeriodEndDate
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

}
