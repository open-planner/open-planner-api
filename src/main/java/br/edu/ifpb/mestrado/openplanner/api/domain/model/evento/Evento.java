package br.edu.ifpb.mestrado.openplanner.api.domain.model.evento;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.TipoNotificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseWithRecorrenciaEntity;

@Entity
@Table(name = "evento", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
@AssociationOverride(name = "notificacoes", joinTable = @JoinTable(
            name = "notificacao_evento",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_notificacao")))
public class Evento extends AuditedBaseWithRecorrenciaEntity<Evento> {

    private static final long serialVersionUID = 533505806464092917L;

    @Override
    public TipoNotificacao tipoNotificacao() {
        return TipoNotificacao.EVENTO;
    }

    @Override
    public Evento newInstance() {
        return new Evento();
    }

}
