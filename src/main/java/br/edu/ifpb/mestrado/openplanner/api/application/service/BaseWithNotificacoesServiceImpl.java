package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.service.BaseManyByUsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseWithNotificacoesEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

public abstract class BaseWithNotificacoesServiceImpl<T extends BaseWithNotificacoesEntity> extends BaseManyByUsuarioServiceImpl<T>
        implements BaseManyByUsuarioService<T> {

    protected NotificacaoService notificacaoService;

    public BaseWithNotificacoesServiceImpl(OAuth2UserDetailsService userDetailsService, NotificacaoService notificacaoService) {
        super(userDetailsService);
        this.notificacaoService = notificacaoService;
    }

    @Override
    @Transactional
    public T save(T entity) {
        if (entity.getNotificacoes() != null) {
            prepareNotificacoes(entity);
        }

        return super.save(entity);
    }

    @Override
    @Transactional
    public T update(Long id, T entity) {
        if (entity.getNotificacoes() != null) {
            prepareNotificacoes(entity);
        }

        return super.update(id, entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        T entity = findById(id);

        if (entity.getNotificacoes() != null) {
            notificacaoService.deleteAll(entity.getNotificacoes());
        }

        super.deleteById(id);
    }

    protected void prepareNotificacoes(T entity) {
        entity.getNotificacoes().stream()
                .forEach(n -> {
                    n.setUsuario(getUsuarioAutenticado());
                    n.setTipo(entity.tipoNotificacao());
                    n.setDescricao(entity.getDescricao());
                });
    }

}
