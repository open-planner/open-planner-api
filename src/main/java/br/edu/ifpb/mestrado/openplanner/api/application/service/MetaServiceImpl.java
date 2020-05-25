package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.meta.Meta;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.MetaService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.MetaRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class MetaServiceImpl extends BaseWithNotificacoesServiceImpl<Meta> implements MetaService {

    private MetaRepository metaRepository;

    public MetaServiceImpl(OAuth2UserDetailsService userDetailsService, NotificacaoService notificacaoService,
            MetaRepository metaRepository) {
        super(userDetailsService, notificacaoService);
        this.metaRepository = metaRepository;
    }

    @Override
    protected MetaRepository getRepository() {
        return metaRepository;
    }

}
