package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseManyByUsuarioEntity;

@NoRepositoryBean
public interface BaseManyByUsuarioRepository<T extends BaseManyByUsuarioEntity> extends BaseRepository<T> {

    List<T> findByUsuarioId(Long id);

}
