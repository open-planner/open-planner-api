package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.PlanoFerias;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.PlanoFeriasService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.PlanoFeriasRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class PlanoFeriasServiceImpl extends BaseManyByUsuarioServiceImpl<PlanoFerias> implements PlanoFeriasService {

    private PlanoFeriasRepository planoFeriasRepository;

    public PlanoFeriasServiceImpl(OAuth2UserDetailsService userDetailsService, PlanoFeriasRepository planoFeriasRepository) {
        super(userDetailsService);
        this.planoFeriasRepository = planoFeriasRepository;
    }

    @Override
    protected PlanoFeriasRepository getRepository() {
        return planoFeriasRepository;
    }

}
