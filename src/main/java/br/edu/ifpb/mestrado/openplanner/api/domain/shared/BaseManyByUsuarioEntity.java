package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Where;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;

@MappedSuperclass
@Where(clause = "excluded = FALSE")
public abstract class BaseManyByUsuarioEntity extends BaseEntity {

    private static final long serialVersionUID = -239743726004732147L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}