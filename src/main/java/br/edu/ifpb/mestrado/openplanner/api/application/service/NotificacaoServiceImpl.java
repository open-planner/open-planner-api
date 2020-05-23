package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.NotificacaoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class NotificacaoServiceImpl extends BaseManyByUsuarioServiceImpl<Notificacao> implements NotificacaoService {

    private NotificacaoRepository notificacaoRepository;

    public NotificacaoServiceImpl(OAuth2UserDetailsService userDetailsService, NotificacaoRepository notificacaoRepository) {
        super(userDetailsService);
        this.notificacaoRepository = notificacaoRepository;
    }

    @Override
    @Transactional
    public void switchLida(Long id) {
        Notificacao notificacao = findById(id);
        notificacao.switchLida();

        save(notificacao);
    }

    @Override
    protected NotificacaoRepository getRepository() {
        return notificacaoRepository;
    }

}
