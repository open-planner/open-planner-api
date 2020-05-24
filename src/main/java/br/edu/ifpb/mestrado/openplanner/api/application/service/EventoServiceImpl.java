package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.EventoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Recorrencia;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.EventoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.BeanUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.DateUtils;

@Service
public class EventoServiceImpl extends BaseManyByUsuarioServiceImpl<Evento> implements EventoService {

    private EventoRepository eventoRepository;

    private NotificacaoService notificacaoService;

    public EventoServiceImpl(OAuth2UserDetailsService userDetailsService, EventoRepository eventoRepository,
            NotificacaoService notificacaoService) {
        super(userDetailsService);
        this.eventoRepository = eventoRepository;
        this.notificacaoService = notificacaoService;
    }

    @Override
    @Transactional
    public Evento save(Evento evento) {
        if (evento.getNotificacoes() != null) {
            evento.getNotificacoes().stream().forEach(n -> n.setUsuario(getUsuarioAutenticado()));
        }

        Evento eventoSaved = super.save(evento);

        if (eventoSaved.isRecorrente()) {
            saveRecorrencias(eventoSaved);
        }

        return eventoSaved;
    }

    @Override
    @Transactional
    public Evento update(Long id, Evento evento) {
        Evento eventoSaved = findById(id);

        if (eventoSaved.getRelacao() != null) {
            evento.setRecorrencia(null);
            evento.setRelacao(eventoSaved.getRelacao());
        }

        if (evento.getNotificacoes() != null) {
            evento.getNotificacoes().stream().forEach(n -> n.setUsuario(getUsuarioAutenticado()));
        }

        Evento eventoUpdated = super.update(id, evento);

        if (eventoUpdated.getRelacao() != null) {
            return eventoUpdated;
        }

        deleteByRelacaoId(id);

        if (eventoUpdated.isRecorrente()) {
            saveRecorrencias(eventoUpdated);
        }

        return eventoUpdated;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Evento evento = findById(id);

        if (evento.getNotificacoes() != null) {
            notificacaoService.deleteAll(evento.getNotificacoes());
        }

        if (evento.getRelacao() == null) {
            deleteByRelacaoId(id);
        }

        super.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByRelacaoId(Long id) {
        List<Evento> eventos = eventoRepository.findByRelacaoId(id);

        if (eventos == null) {
            return;
        }

        eventos.stream().forEach(e -> deleteById(e.getId()));
    }

    @Override
    protected EventoRepository getRepository() {
        return eventoRepository;
    }

    private void saveRecorrencias(Evento evento) {
        Recorrencia recorrencia = evento.getRecorrencia();
        LocalDateTime dataHora = evento.getDataHora();

        if (!recorrencia.getDataLimite().isAfter(dataHora.toLocalDate())) {
            return;
        }

        dataHora = DateUtils.plusOne(dataHora, recorrencia.getUnidade());

        List<Notificacao> notificacoes = null;

        if (evento.getNotificacoes() != null) {
            notificacoes = plusOneDataNewNotificacoes(evento.getNotificacoes(), recorrencia);
        }

        while (!dataHora.toLocalDate().isAfter(recorrencia.getDataLimite())) {
            Evento eventoCopy = new Evento();
            BeanUtils.copyProperties(evento, eventoCopy, "id", "recorrencia", "notificacoes");
            eventoCopy.setRelacao(evento);
            eventoCopy.setDataHora(dataHora);
            eventoCopy.setNotificacoes(notificacoes);

            super.save(eventoCopy);

            dataHora = DateUtils.plusOne(dataHora, recorrencia.getUnidade());

            if (notificacoes != null) {
                notificacoes = plusOneDataNewNotificacoes(notificacoes, recorrencia);
            }
        }
    }

    private List<Notificacao> plusOneDataNewNotificacoes(List<Notificacao> notificacoes, Recorrencia recorrencia) {
        return notificacoes.stream()
                .map(n -> {
                    Notificacao notificacao = new Notificacao();
                    BeanUtils.copyProperties(n, notificacao, "id");
                    notificacao.setDataHora(DateUtils.plusOne(notificacao.getDataHora(), recorrencia.getUnidade()));

                    return notificacao;
                }).collect(Collectors.toList());
    }

}
