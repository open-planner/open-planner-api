package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.BusinessException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Viagem;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.ViagemService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Periodo;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.ViagemRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class ViagemServiceImpl extends BaseManyByUsuarioServiceImpl<Viagem> implements ViagemService {

    private ViagemRepository viagemRepository;

    public ViagemServiceImpl(OAuth2UserDetailsService userDetailsService, ViagemRepository viagemRepository) {
        super(userDetailsService);
        this.viagemRepository = viagemRepository;
    }

    @Override
    public Viagem save(Viagem viagem) {
        checkPeriodo(viagem);

        return super.save(viagem);
    }

    @Override
    public Viagem update(Long id, Viagem viagem) {
        checkPeriodo(viagem);
        return super.update(id, viagem);
    }

    @Override
    protected ViagemRepository getRepository() {
        return viagemRepository;
    }

    private void checkPeriodo(Viagem viagem) {
        Periodo periodo = viagem.getPeriodo();

        if (periodo.getDataInicio().isAfter(periodo.getDataFim())) {
            throw new BusinessException("periodo.inicio-posterior-fim");
        }
    }

}
