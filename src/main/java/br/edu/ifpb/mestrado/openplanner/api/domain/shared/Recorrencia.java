package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Embeddable
public class Recorrencia implements Serializable {

    private static final long serialVersionUID = 7071130319329925063L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_recorrencia")
    private RecorrenciaTimeUnit unidade;

    @NotNull
    @Column(name = "data_limite_recorrencia")
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
