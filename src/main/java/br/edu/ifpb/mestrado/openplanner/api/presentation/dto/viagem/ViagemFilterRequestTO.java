package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Tipo;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecBetween;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;

public class ViagemFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -2693360313523783449L;

    private Long id;

    private Tipo tipo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @SpecBetween(left = "periodo.dataInicio", right = "periodo.dataFim")
    private LocalDate data;

    // TODO checar possibilidade
//    @SpecGroup(operator = Operator.OR)
//    private PeriodoFilterRequestTO periodo;

    @SpecField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
    private String destino;

    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

//    public PeriodoFilterRequestTO getPeriodo() {
//        return periodo;
//    }
//
//    public void setPeriodo(PeriodoFilterRequestTO periodo) {
//        this.periodo = periodo;
//    }

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
