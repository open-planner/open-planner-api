package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseWithRecorrenciaEntity;

@NoRepositoryBean
public interface BaseWithRecorrenciaRepository<T extends BaseWithRecorrenciaEntity<?>>
        extends BaseWithNotificacoesRepository<T> {

    List<T> findByRelacaoId(Long id);

}
