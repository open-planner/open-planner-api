package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.RodaVida;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.RodaVidaRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class RodaVidaServiceImpl extends BaseServiceImpl<RodaVida> {

    private RodaVidaRepository rodaVidaRepository;

    public RodaVidaServiceImpl(OAuth2UserDetailsService userDetailsService, RodaVidaRepository rodaVidaRepository) {
        super(userDetailsService);
        this.rodaVidaRepository = rodaVidaRepository;
    }

    @Override
    protected BaseRepository<RodaVida> getRepository() {
        return rodaVidaRepository;
    }

}
