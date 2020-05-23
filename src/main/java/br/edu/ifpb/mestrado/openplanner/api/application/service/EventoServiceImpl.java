package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.EventoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.EventoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.RecorrenciaRequestTO;

@Service
public class EventoServiceImpl extends BaseManyByUsuarioServiceImpl<Evento> implements EventoService {

    private EventoRepository eventoRepository;

    public EventoServiceImpl(OAuth2UserDetailsService userDetailsService, EventoRepository eventoRepository) {
        super(userDetailsService);
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public Evento save(Evento evento, RecorrenciaRequestTO recorrencia) {
        if (evento.getNotificacoes() != null) {
            evento.getNotificacoes().forEach(n -> n.setUsuario(getUsuarioAutenticado()));
        }

        return super.save(evento);
    }

    @Override
    protected EventoRepository getRepository() {
        return eventoRepository;
    }

}
