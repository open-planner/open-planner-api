package br.edu.ifpb.mestrado.openplanner.api.domain.model.evento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseManyByUsuarioEntity;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Recorrencia;

@Entity
@Table(name = "evento", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
public class Evento extends AuditedBaseManyByUsuarioEntity {

    private static final long serialVersionUID = 533505806464092917L;

    @NotNull
    private LocalDateTime dataHora;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    @Valid
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(
            name = "notificacao_evento",
            schema = "planner",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_notificacao"))
    private List<Notificacao> notificacoes;

    @Valid
    @Embedded
    private Recorrencia recorrencia;

    @Valid
    @ManyToOne
    @JoinColumn(name = "id_relacao")
    private Evento relacao;

    public Boolean isRecorrente() {
        return recorrencia != null && recorrencia.getUnidade() != null && recorrencia.getDataLimite() != null;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        if (this.notificacoes == null) {
            this.notificacoes = new ArrayList<>(notificacoes);
            return;
        }

        this.notificacoes.clear();
        this.notificacoes.addAll(notificacoes);
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
