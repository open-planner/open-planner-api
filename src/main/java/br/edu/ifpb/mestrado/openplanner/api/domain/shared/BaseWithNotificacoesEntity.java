package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.TipoNotificacao;

@MappedSuperclass
public abstract class BaseWithNotificacoesEntity extends BaseManyByUsuarioEntity {

    private static final long serialVersionUID = -696365959925024080L;

    @NotBlank
    @Size(min = 3, max = 64)
    protected String descricao;

    @Valid
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    protected List<Notificacao> notificacoes;

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
        if (notificacoes == null) {
            this.notificacoes = null;
            return;
        }

        if (this.notificacoes == null) {
            this.notificacoes = new ArrayList<>(notificacoes);
            return;
        }

        this.notificacoes.clear();
        this.notificacoes.addAll(notificacoes);
    }

    public abstract TipoNotificacao tipoNotificacao();

}
