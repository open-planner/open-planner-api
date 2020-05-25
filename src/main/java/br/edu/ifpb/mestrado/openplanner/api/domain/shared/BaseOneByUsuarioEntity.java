package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;

@MappedSuperclass
public abstract class BaseOneByUsuarioEntity extends BaseEntity {

    private static final long serialVersionUID = -239743726004732147L;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", unique = true, nullable = false)
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
