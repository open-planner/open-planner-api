package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.rodavida.RodaVida;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.RodaVidaService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseOneByUsuarioRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.RodaVidaRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class RodaVidaServiceImpl extends BaseOneByUsuarioServiceImpl<RodaVida> implements RodaVidaService {

    private RodaVidaRepository rodaVidaRepository;

    public RodaVidaServiceImpl(OAuth2UserDetailsService userDetailsService, RodaVidaRepository rodaVidaRepository) {
        super(userDetailsService);
        this.rodaVidaRepository = rodaVidaRepository;
    }

    @Transactional
    @Override
    public RodaVida findByUsuarioAutenticado() {
        RodaVida rodaVida;

        try {
            rodaVida = super.findByUsuarioAutenticado();
        } catch (InformationNotFoundException informationNotFoundException) {
            rodaVida = generateNew();
        }

        return rodaVida;
    }

    @Override
    protected BaseOneByUsuarioRepository<RodaVida> getRepository() {
        return rodaVidaRepository;
    }

    private RodaVida generateNew() {
        RodaVida rodaVida = new RodaVida();

        return this.save(rodaVida);
    }

}
