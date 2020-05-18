package br.edu.ifpb.mestrado.openplanner.api.domain.model.tag;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseManyByUsuarioEntity;

@Entity
@Table(name = "tag", schema = "planner")
@Where(clause = "excluded = 'FALSE'")
public class Tag extends AuditedBaseManyByUsuarioEntity {

    private static final long serialVersionUID = -8829963347764690919L;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    @Size(min = 3, max = 6)
    private String cor;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

}
