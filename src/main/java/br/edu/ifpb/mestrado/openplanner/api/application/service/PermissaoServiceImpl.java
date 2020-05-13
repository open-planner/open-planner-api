package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.PermissaoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.PermissaoRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class PermissaoServiceImpl extends BaseServiceImpl<Permissao> implements PermissaoService {

    private PermissaoRepository permissaoRepository;

    public PermissaoServiceImpl(OAuth2UserDetailsService userDetailsService, PermissaoRepository permissaoRepository) {
        super(userDetailsService);
        this.permissaoRepository = permissaoRepository;
    }

    @Override
    @Cacheable("PermissaoServiceImpl_findAll")
    public List<Permissao> findAll() {
        return super.findAll();
    }

    @Override
    protected BaseRepository<Permissao> getRepository() {
        return permissaoRepository;
    }

}
