package br.edu.ifpb.mestrado.openplanner.api.application.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.TagService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.TagRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Service
public class TagServiceImpl extends BaseManyByUsuarioServiceImpl<Tag> implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl(OAuth2UserDetailsService userDetailsService, TagRepository tagRepository) {
        super(userDetailsService);
        this.tagRepository = tagRepository;
    }

    @Override
    protected TagRepository getRepository() {
        return tagRepository;
    }

}
