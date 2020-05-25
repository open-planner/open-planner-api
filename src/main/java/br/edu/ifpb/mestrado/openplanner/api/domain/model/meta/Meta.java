package br.edu.ifpb.mestrado.openplanner.api.domain.model.meta;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.TipoNotificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseWithNotificacoesEntity;

@Entity
@Table(name = "meta", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
@AssociationOverride(name = "notificacoes", joinTable = @JoinTable(
        name = "notificacao_meta",
        schema = "planner",
        joinColumns = @JoinColumn(name = "id_meta"),
        inverseJoinColumns = @JoinColumn(name = "id_notificacao")))
public class Meta extends AuditedBaseWithNotificacoesEntity {

    private static final long serialVersionUID = -1690705150911762115L;

    @NotNull
    private LocalDate data;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String anotacoes;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "tag_meta",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_meta"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> tags;

    @Override
    public TipoNotificacao tipoNotificacao() {
        return TipoNotificacao.META;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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
