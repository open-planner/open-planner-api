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
import br.edu.ifpb.mestrado.openplanner.api.domain.service.BaseManyByUsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseManyByUsuarioEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseManyByUsuarioRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.BaseManyByUsuarioEntitySpecification;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

public abstract class BaseManyByUsuarioServiceImpl<T extends BaseManyByUsuarioEntity> extends BaseServiceImpl<T>
        implements BaseManyByUsuarioService<T> {

    public BaseManyByUsuarioServiceImpl(OAuth2UserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findByUsuarioAutenticado() {
        return getRepository().findByUsuarioId(getUsuarioAutenticado().getId());
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
        return getRepository().findAll(BaseManyByUsuarioEntitySpecification.usuarioId(getUsuarioAutenticado().getId()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(BaseManyByUsuarioEntitySpecification.usuarioId(getUsuarioAutenticado().getId()), pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return getRepository().findAll(Specification
                .where(specification)
                .and(BaseManyByUsuarioEntitySpecification.usuarioId(getUsuarioAutenticado().getId())), pageable);
    }

    @Override
    public T save(T entity) {
        entity.setUsuario(getUsuarioAutenticado());

        return super.save(entity);
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

    @Transactional
    @Override
    public void deleteAll(Iterable<T> entities) {
        entities.forEach(e -> deleteById(e.getId()));
    }

    protected void checkUsuario(Usuario usuario) {
        if (!usuario.equals(getUsuarioAutenticado()) && !getUsuarioAutenticado().isRootOrAdmin()) {
            throw new UnauthorizedUserException();
        }
    }

    protected abstract BaseManyByUsuarioRepository<T> getRepository();

}
