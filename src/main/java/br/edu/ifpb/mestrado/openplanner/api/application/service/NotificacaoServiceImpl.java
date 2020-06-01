package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.factory.MailFactory;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.NotificacaoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.service.MailService;

@Service
public class NotificacaoServiceImpl extends BaseManyByUsuarioServiceImpl<Notificacao> implements NotificacaoService {

    private NotificacaoRepository notificacaoRepository;

    private MailService mailService;

    private MailFactory mailFactory;

    public NotificacaoServiceImpl(OAuth2UserDetailsService userDetailsService, NotificacaoRepository notificacaoRepository,
            MailService mailService, MailFactory mailFactory) {
        super(userDetailsService);
        this.notificacaoRepository = notificacaoRepository;
        this.mailService = mailService;
        this.mailFactory = mailFactory;
    }

    @Override
    @Transactional
    public void switchLida(Long id) {
        Notificacao notificacao = findById(id);
        notificacao.switchLida();

        save(notificacao);
    }

    @Override
    @Scheduled(cron = "${open-planner-api.cron.notificacao-service.send-mails}")
    @Transactional
    public void sendMails() {
        List<Notificacao> notificacoes = notificacaoRepository
                .findByDataHoraLessThanEqualAndEmailOrderByDataHoraAscTipoAsc(LocalDateTime.now(), true);

        if (notificacoes == null || notificacoes.isEmpty()) {
            return;
        }

        notificacoes.stream().forEach(n -> n.setEmail(false));
        mailService.send(mailFactory.createNotificacoes(notificacoes));

        getUserDetailsService().setAuthById(Usuario.ID_SYSTEM);
        notificacaoRepository.saveAll(notificacoes);
    }

    @Override
    protected NotificacaoRepository getRepository() {
        return notificacaoRepository;
    }

}
