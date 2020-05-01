package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario_;

public class UsuarioSpecification {

    public static Specification<Usuario> positiveIdAndActiveAndNotPendenteAndNotBloqueado() {
        return Specification
                .where(BaseEntitySpecification.<Usuario>positiveIdAndActive())
                .and(notPendente())
                .and(notBloqueado());
    }

    public static Specification<Usuario> notPendente() {
        return new SpecificationFactory<Usuario>().create(Usuario_.PENDENTE, false);
    }

    public static Specification<Usuario> notBloqueado() {
        return new SpecificationFactory<Usuario>().create(Usuario_.BLOQUEADO, false);
    }

}
