package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseWithRecorrenciaEntity<T extends BaseWithRecorrenciaEntity<T>> extends BaseWithNotificacoesEntity {

    private static final long serialVersionUID = -2003962831066734262L;

    @NotNull
    protected LocalDateTime dataHora;

    @Valid
    @Embedded
    protected Recorrencia recorrencia;

    @Valid
    @ManyToOne
    @JoinColumn(name = "id_relacao")
    protected T relacao;

    public Boolean isRecorrente() {
        return recorrencia != null && recorrencia.getUnidade() != null && recorrencia.getDataLimite() != null;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Recorrencia getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(Recorrencia recorrencia) {
        this.recorrencia = recorrencia;
    }

    public T getRelacao() {
        return relacao;
    }

    public void setRelacao(T relacao) {
        this.relacao = relacao;
    }

    public abstract BaseWithRecorrenciaEntity<T> newInstance();

}
