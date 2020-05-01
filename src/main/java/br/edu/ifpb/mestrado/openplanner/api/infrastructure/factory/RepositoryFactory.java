package br.edu.ifpb.mestrado.openplanner.api.infrastructure.factory;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {

    private EntityManager entityManager;

    public RepositoryFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> SimpleJpaRepository<T, Long> create(Class<T> domainClass) {
        return new SimpleJpaRepository<>(domainClass, entityManager);
    }

}
