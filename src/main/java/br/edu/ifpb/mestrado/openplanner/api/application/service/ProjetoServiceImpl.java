package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Projeto;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.ProjetoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.ProjetoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class ProjetoServiceImpl extends BaseWithNotificacoesServiceImpl<Projeto> implements ProjetoService {

    private ProjetoRepository projetoRepository;

    public ProjetoServiceImpl(OAuth2UserDetailsService userDetailsService, NotificacaoService notificacaoService,
            ProjetoRepository projetoRepository) {
        super(userDetailsService, notificacaoService);
        this.projetoRepository = projetoRepository;
    }

    @Override
    protected ProjetoRepository getRepository() {
        return projetoRepository;
    }

}
