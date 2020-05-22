package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class NotificacaoRequestTO implements Serializable {

    private static final long serialVersionUID = -8691528433237011818L;

    @NotNull
    private Integer tempo;

    @NotNull
    private NotificacaoTimeUnit unidade;

    @NotNull
    private Boolean email;

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public NotificacaoTimeUnit getUnidade() {
        return unidade;
    }

    public void setUnidade(NotificacaoTimeUnit unidade) {
        this.unidade = unidade;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
        result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotificacaoRequestTO other = (NotificacaoRequestTO) obj;
        if (tempo == null) {
            if (other.tempo != null)
                return false;
        } else if (!tempo.equals(other.tempo))
            return false;
        if (unidade != other.unidade)
            return false;
        return true;
    }

}
