package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Periodo;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecEntity;

@SpecEntity(Periodo.class)
public class PeriodoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 6907306552014617874L;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
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
