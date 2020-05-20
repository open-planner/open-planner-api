package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias;

import java.io.Serializable;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.Status;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoResponseTO;

public class PlanoFeriasFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 5916096502268452705L;

    private Long id;

    private PeriodoResponseTO periodo;

    @SpecField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
    private String destino;

    private Status status;

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

}
