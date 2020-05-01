package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.util.List;
import java.util.Optional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;

public interface UsuarioRepository extends BaseRepository<Usuario> {

    Optional<Usuario> findByEmailIgnoreCase(String email);

    Optional<Usuario> findBySenhaResetToken(String resetToken);

    List<Usuario> findByAtivoAndPendenteAndBloqueado(Boolean ativo, Boolean pendente, Boolean bloqueado);

}
