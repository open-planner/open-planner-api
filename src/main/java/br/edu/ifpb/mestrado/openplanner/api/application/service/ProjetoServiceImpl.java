package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.BusinessException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Projeto;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.ProjetoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Periodo;
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
    public Projeto save(Projeto projeto) {
        checkPeriodo(projeto);
        return super.save(projeto);
    }

    @Override
    public Projeto update(Long id, Projeto projeto) {
        checkPeriodo(projeto);
        return super.update(id, projeto);
    }

    @Override
    protected ProjetoRepository getRepository() {
        return projetoRepository;
    }

    private void checkPeriodo(Projeto projeto) {
        Periodo periodo = projeto.getPeriodo();

        if (periodo.getDataInicio().isAfter(periodo.getDataFim())) {
            throw new BusinessException("periodo.inicio-posterior-fim");
        }
    }

}
