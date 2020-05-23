package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.EventoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.EventoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.BeanUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.DateUtils;
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

    private void saveRecorrencias(Evento evento, RecorrenciaRequestTO recorrencia) {
        if (!recorrencia.getDataLimite().isAfter(evento.getDataHora().toLocalDate())) {
            return;
        }

        LocalDateTime dataHora = DateUtils.plus(evento.getDataHora(), recorrencia.getUnidade());

        do {
            Evento eventoCopy = new Evento();
            BeanUtils.copyProperties(evento, eventoCopy, "id");
            eventoCopy.setRelacao(evento);
            eventoCopy.setDataHora(dataHora);

            if (eventoCopy.getNotificacoes() != null) {
                List<Notificacao> notificacoes = eventoCopy.getNotificacoes().stream().map(n -> {
                    Notificacao notificacao = new Notificacao();
                    BeanUtils.copyProperties(n, notificacao, "id");

                    return notificacao;
                }).collect(Collectors.toList());
                eventoCopy.setNotificacoes(notificacoes);
            }

            save(eventoCopy);

            dataHora = DateUtils.plus(dataHora, recorrencia.getUnidade());
        } while (!dataHora.toLocalDate().isAfter(recorrencia.getDataLimite()));
    }

}
