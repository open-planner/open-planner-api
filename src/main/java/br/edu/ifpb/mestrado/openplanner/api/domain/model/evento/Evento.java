package br.edu.ifpb.mestrado.openplanner.api.domain.model.evento;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseManyByUsuarioEntity;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Periodo;

@Entity
@Table(name = "evento", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
public class Evento extends AuditedBaseManyByUsuarioEntity {

    private static final long serialVersionUID = 533505806464092917L;

    @Valid
    private Periodo periodo;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    @OneToMany(orphanRemoval = true)
    @JoinTable(
            name = "notificacao_evento",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_notificacao"))
    private Set<Notificacao> notificacoes;

    private Evento relacao;

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public Evento getRelacao() {
        return relacao;
    }

    public void setRelacao(Evento relacao) {
        this.relacao = relacao;
    }

}
