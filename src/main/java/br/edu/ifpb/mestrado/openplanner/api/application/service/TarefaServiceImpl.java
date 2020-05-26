package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa.Tarefa;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.TarefaService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.TarefaRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class TarefaServiceImpl extends BaseWithRecorrenciaServiceImpl<Tarefa> implements TarefaService {

    private TarefaRepository tarefaRepository;

    public TarefaServiceImpl(OAuth2UserDetailsService userDetailsService, NotificacaoService notificacaoService,
            TarefaRepository tarefaRepository) {
        super(userDetailsService, notificacaoService);
        this.tarefaRepository = tarefaRepository;
    }

    @Override
    protected TarefaRepository getRepository() {
        return tarefaRepository;
    }

}
