package br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto;

import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.TipoNotificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseWithNotificacoesEntity;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Periodo;

@Entity
@Table(name = "projeto", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
@AssociationOverride(name = "notificacoes", joinTable = @JoinTable(
        name = "notificacao_projeto",
        schema = "planner",
        joinColumns = @JoinColumn(name = "id_projeto"),
        inverseJoinColumns = @JoinColumn(name = "id_notificacao")))
public class Projeto extends AuditedBaseWithNotificacoesEntity {

    private static final long serialVersionUID = 1272900146705036715L;

    @Valid
    @Embedded
    private Periodo periodo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String anotacoes;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "tag_projeto",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_projeto"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> tags;

    @Override
    public TipoNotificacao tipoNotificacao() {
        return TipoNotificacao.PROJETO;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
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
        this.tags = tags;
    }

}
