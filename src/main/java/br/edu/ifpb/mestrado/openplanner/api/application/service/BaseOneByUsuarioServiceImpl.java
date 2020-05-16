package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.UnauthorizedUserException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.BaseOneByUsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseOneByUsuarioEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseOneByUsuarioRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.BaseOneByUsuarioEntitySpecification;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

public abstract class BaseOneByUsuarioServiceImpl<T extends BaseOneByUsuarioEntity> extends BaseServiceImpl<T>
        implements BaseOneByUsuarioService<T> {

    public BaseOneByUsuarioServiceImpl(OAuth2UserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    @Transactional(readOnly = true)
    @Override
    public T findByUsuarioAutenticado() {
        Optional<T> entityOpt = getRepository().findByUsuarioId(getUsuarioAutenticado().getId());

        return entityOpt.orElseThrow(() -> new InformationNotFoundException());
    }

    @Transactional(readOnly = true)
    @Override
    public T findById(Long id) {
        Optional<T> entityOpt = getRepository().findById(id);
        T entity = entityOpt.orElseThrow(() -> new InformationNotFoundException());

        checkUsuario(entity.getUsuario());

        return entity;
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll() {
        return getRepository().findAll(BaseOneByUsuarioEntitySpecification.usuarioId(getUsuarioAutenticado().getId()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(BaseOneByUsuarioEntitySpecification.usuarioId(getUsuarioAutenticado().getId()), pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return getRepository().findAll(Specification
                .where(specification)
                .and(BaseOneByUsuarioEntitySpecification.usuarioId(getUsuarioAutenticado().getId())), pageable);
    }

    @Override
    public T save(T entity) {
        entity.setUsuario(getUsuarioAutenticado());

        return super.save(entity);
    }

    @Override
    public T updateByUsuarioAutenticado(T entity) {
        T savedEntity = findByUsuarioAutenticado();

        return super.update(savedEntity.getId(), entity);
    }

    @Transactional
    @Override
    public T update(Long id, T entity) {
        findById(id);

        return super.update(id, entity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id);
        super.deleteById(id);
    }

    protected void checkUsuario(Usuario usuario) {
        if (!usuario.equals(getUsuarioAutenticado()) && !getUsuarioAutenticado().isRootOrAdmin()) {
            throw new UnauthorizedUserException();
        }
    }

    protected abstract BaseOneByUsuarioRepository<T> getRepository();

}
