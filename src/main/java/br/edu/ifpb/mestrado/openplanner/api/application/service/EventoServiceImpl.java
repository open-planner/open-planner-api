package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.EventoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.EventoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.BeanUtils;
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
            evento.getNotificacoes().stream().forEach(n -> n.setUsuario(getUsuarioAutenticado()));
        }

        Evento eventoSaved = super.save(evento);

        if (recorrencia != null) {
            saveRecorrencias(eventoSaved, recorrencia);
        }

        return eventoSaved;
    }

    @Override
    protected EventoRepository getRepository() {
        return eventoRepository;
    }

    public void saveRecorrencias(Evento evento, RecorrenciaRequestTO recorrencia) {
//        Set<Evento> eventos = new HashSet<>();
//        BeanUtils.copyProperties(evento, );
//        LocalDate
    }

}
