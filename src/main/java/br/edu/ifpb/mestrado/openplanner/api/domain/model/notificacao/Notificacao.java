package br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseManyByUsuarioEntity;

@Entity
@Table(name = "notificacao", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
public class Notificacao extends AuditedBaseManyByUsuarioEntity {

    private static final long serialVersionUID = 5230523475587670588L;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipo;

    @NotBlank
    @Size(max = 128)
    private String descricao;

    private Boolean lida = false;

    private Boolean email = false;

    public void switchLida() {
        lida = !lida;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacao tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

}
