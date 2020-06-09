package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.TagService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecFactory;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tag.TagResponseTO;

@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    private ModelMapperFacade modelMapperFacade;

    public TagController(TagService tagService, ModelMapperFacade modelMapperFacade) {
        super();
        this.tagService = tagService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<TagResponseTO>> findAll(TagFilterRequestTO tagFilterRequestTO, Pageable pageable) {
        Specification<Tag> specification = new SpecFactory<Tag>().create(tagFilterRequestTO, Tag.class);
        Page<Tag> page = tagService.findAll(specification, pageable);
        Page<TagResponseTO> responseTOPage = modelMapperFacade.map(page, TagResponseTO.class);

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseTO> findById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        TagResponseTO responseTO = modelMapperFacade.map(tag, TagResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PostMapping
    public ResponseEntity<TagResponseTO> save(@Valid @RequestBody TagRequestTO tagRequestTO) {
        Tag tag = modelMapperFacade.map(tagRequestTO, Tag.class);
        Tag tagSaved = tagService.save(tag);
        TagResponseTO responseTO = modelMapperFacade.map(tagSaved, TagResponseTO.class);

        return ResponseEntityFacade.created(responseTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseTO> update(@PathVariable Long id, @Valid @RequestBody TagRequestTO tagRequestTO) {
        Tag tag = modelMapperFacade.map(tagRequestTO, Tag.class);
        Tag savedTag = tagService.update(id, tag);
        TagResponseTO responseTO = modelMapperFacade.map(savedTag, TagResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }

}
