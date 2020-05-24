package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseOneByUsuarioEntity;

@NoRepositoryBean
public interface BaseOneByUsuarioRepository<T extends BaseOneByUsuarioEntity> extends BaseRepository<T> {

    Optional<T> findByUsuarioId(Long id);

}
