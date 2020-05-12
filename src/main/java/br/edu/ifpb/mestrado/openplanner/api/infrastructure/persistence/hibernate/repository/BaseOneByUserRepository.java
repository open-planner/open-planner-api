package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseOneByUserRepository<T> extends BaseRepository<T> {

    Optional<T> findByUsuarioId(Long id);

}
