package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.BaseService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.BaseEntitySpecification;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.BeanUtils;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private OAuth2UserDetailsService userDetailsService;

    public BaseServiceImpl(OAuth2UserDetailsService userDetailsService) {
        super();
        this.userDetailsService = userDetailsService;
    }

    @Transactional(readOnly = true)
    @Override
    public T findById(Long id) {
        Optional<T> entityOpt = getRepository().findById(id);

        return entityOpt.orElseThrow(() -> new InformationNotFoundException());
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll() {
        if (getUsuarioAutenticado().isRoot()) {
            return getRepository().findAll();
        }

        return getRepository().findAll(BaseEntitySpecification.positiveId());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Pageable pageable) {
        if (getUsuarioAutenticado().isRoot()) {
            return getRepository().findAll(pageable);
        }

        return getRepository().findAll(BaseEntitySpecification.positiveId(), pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        if (!getUsuarioAutenticado().isRoot()) {
            return getRepository().findAll(Specification
                    .where(specification)
                    .and(BaseEntitySpecification.positiveId()), pageable);
        }

        return getRepository().findAll(specification, pageable);
    }

    @Transactional
    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public T update(Long id, T entity) {
        T savedEntity = findById(id);
        BeanUtils.copyProperties(entity, savedEntity);

        return getRepository().save(savedEntity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        T entity = findById(id);
        entity.setExcluded(true);
        entity.setExcludedAt(LocalDate.now());
    }

    @Transactional
    @Override
    public void deleteAll(Iterable<T> entities) {
        entities.forEach(e -> deleteById(e.getId()));
    }

    protected Usuario getUsuarioAutenticado() {
        return userDetailsService.getUsuarioAuth().getUsuario();
    }

    protected OAuth2UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    protected abstract BaseRepository<T> getRepository();

}
