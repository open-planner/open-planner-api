package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import org.springframework.data.repository.NoRepositoryBean;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseWithNotificacoesEntity;

@NoRepositoryBean
public interface BaseWithNotificacoesRepository<T extends BaseWithNotificacoesEntity> extends BaseManyByUsuarioRepository<T> {

}
