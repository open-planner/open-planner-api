package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseManyByUserRepository<T> extends BaseRepository<T> {

    List<T> findByUsuarioId(Long id);

}
