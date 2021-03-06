package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.github.fagnerlima.springspecificationtools.SpecOperation;
import com.github.fagnerlima.springspecificationtools.annotation.SpecBetween;
import com.github.fagnerlima.springspecificationtools.annotation.SpecEntity;
import com.github.fagnerlima.springspecificationtools.annotation.SpecField;
import com.github.fagnerlima.springspecificationtools.annotation.SpecPeriod;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Tipo;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Viagem;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoFilterRequestTO;

@SpecEntity(Viagem.class)
public class ViagemFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -2693360313523783449L;

    private Long id;

    private Tipo tipo;

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
