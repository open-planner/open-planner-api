package br.edu.ifpb.mestrado.openplanner.api.domain.model.evento;

import java.time.LocalDateTime;

import javax.persistence.AssociationOverride;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.TipoNotificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseWithNotificationsEntity;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Recorrencia;

@Entity
@Table(name = "evento", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
@AssociationOverride(name = "notificacoes", joinTable = @JoinTable(
            name = "notificacao_evento",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_notificacao")))
public class Evento extends AuditedBaseWithNotificationsEntity {

    private static final long serialVersionUID = 533505806464092917L;

    @NotNull
    private LocalDateTime dataHora;

    @Valid
    @Embedded
    private Recorrencia recorrencia;

    @Valid
    @ManyToOne
    @JoinColumn(name = "id_relacao")
    private Evento relacao;

    @Override
    public TipoNotificacao tipoNotificacao() {
        return TipoNotificacao.EVENTO;
    }

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

    public Evento getRelacao() {
        return relacao;
    }

    public void setRelacao(Evento relacao) {
        this.relacao = relacao;
    }

}
