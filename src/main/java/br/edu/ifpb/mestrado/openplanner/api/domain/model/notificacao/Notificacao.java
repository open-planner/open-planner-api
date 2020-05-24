package br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseManyByUsuarioEntity;

@Entity
@Table(name = "notificacao", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
public class Notificacao extends AuditedBaseManyByUsuarioEntity {

    private static final long serialVersionUID = 5230523475587670588L;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime dataHora;

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
