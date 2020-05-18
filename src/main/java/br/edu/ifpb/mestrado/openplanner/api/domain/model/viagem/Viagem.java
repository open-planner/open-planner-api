package br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseManyByUsuarioEntity;

@Entity
@Table(name = "viagem", schema = "planner")
public class Viagem extends AuditedBaseManyByUsuarioEntity {

    private static final long serialVersionUID = 3892266534488189247L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Tipo tipo;

    @NotNull
    @Column
    private LocalDate data;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column
    private String destino;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Column
    private String anotacoes;

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
