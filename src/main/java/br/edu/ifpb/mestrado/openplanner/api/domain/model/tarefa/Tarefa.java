package br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.TipoNotificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseWithRecorrenciaEntity;

@Entity
@Table(name = "tarefa", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
@AssociationOverride(name = "notificacoes", joinTable = @JoinTable(
            name = "notificacao_tarefa",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_tarefa"),
            inverseJoinColumns = @JoinColumn(name = "id_notificacao")))
public class Tarefa extends AuditedBaseWithRecorrenciaEntity<Tarefa> {

    private static final long serialVersionUID = -1516198226580079885L;

    private Status status;

    private String anotacoes;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "tag_tarefa",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_tarefa"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> tags;

    @Override
    public Tarefa newInstance() {
        return new Tarefa();
    }

    @Override
    public TipoNotificacao tipoNotificacao() {
        return TipoNotificacao.TAREFA;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        if (tags == null) {
            this.tags = null;
            return;
        }

        if (this.tags == null) {
            this.tags = new HashSet<>(tags);
            return;
        }

        this.tags.clear();
        this.tags.addAll(tags);
    }

}
