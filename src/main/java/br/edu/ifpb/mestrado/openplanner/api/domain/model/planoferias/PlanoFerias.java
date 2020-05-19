package br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseManyByUsuarioEntity;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Periodo;

@Entity
@Table(name = "plano_ferias", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
public class PlanoFerias extends AuditedBaseManyByUsuarioEntity {

    private static final long serialVersionUID = 5498731772113637451L;

    @Valid
    @Embedded
    private Periodo periodo;

    @NotBlank
    @Size(min = 3, max = 100)
    private String destino;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String anotacoes;

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
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

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

}
