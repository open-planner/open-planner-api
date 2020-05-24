package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.EventoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.EventoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class EventoServiceImpl extends BaseWithRecorrenciaServiceImpl<Evento> implements EventoService {

    private EventoRepository eventoRepository;

    public EventoServiceImpl(OAuth2UserDetailsService userDetailsService, EventoRepository eventoRepository,
            NotificacaoService notificacaoService) {
        super(userDetailsService, notificacaoService);
        this.eventoRepository = eventoRepository;
    }

    @Override
    protected EventoRepository getRepository() {
        return eventoRepository;
    }

}
