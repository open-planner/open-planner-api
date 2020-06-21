package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.PlanoFerias;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.Status;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecOperation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecBetween;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecPeriod;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoFilterRequestTO;

@SpecEntity(PlanoFerias.class)
public class PlanoFeriasFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 5916096502268452705L;

    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @SpecBetween(left = "periodo.dataInicio", right = "periodo.dataFim")
    private LocalDate data;

    @SpecPeriod(start = "periodo.dataInicio", end = "periodo.dataFim")
    private PeriodoFilterRequestTO periodo;

    @SpecField(operation = SpecOperation.LIKE_IGNORE_CASE_UNACCENT)
    private String destino;

    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public PeriodoFilterRequestTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoFilterRequestTO periodo) {
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

}
