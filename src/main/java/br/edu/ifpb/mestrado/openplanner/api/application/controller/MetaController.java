package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import java.util.List;

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
import br.edu.ifpb.mestrado.openplanner.api.domain.model.meta.Meta;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.meta.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.MetaService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecBuilder;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta.MetaFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta.MetaReducedResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta.MetaRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta.MetaResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.EnumOptionTO;

@RestController
@RequestMapping("/metas")
public class MetaController {

    private MetaService metaService;

    private ModelMapperFacade modelMapperFacade;

    public MetaController(MetaService metaService, ModelMapperFacade modelMapperFacade) {
        super();
        this.metaService = metaService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<MetaReducedResponseTO>> findAll(MetaFilterRequestTO metaFilterRequestTO, Pageable pageable) {
        Specification<Meta> specification = new SpecBuilder<Meta>().add(metaFilterRequestTO).build();
        Page<Meta> page = metaService.findAll(specification, pageable);
        Page<MetaReducedResponseTO> responseTOPage = modelMapperFacade.map(page, MetaReducedResponseTO.class);

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaResponseTO> findById(@PathVariable Long id) {
        Meta meta = metaService.findById(id);
        MetaResponseTO responseTO = modelMapperFacade.map(meta, MetaResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PostMapping
    public ResponseEntity<MetaResponseTO> save(@Valid @RequestBody MetaRequestTO metaRequestTO) {
        Meta meta = modelMapperFacade.map(metaRequestTO, Meta.class);
        Meta metaSaved = metaService.save(meta);
        MetaResponseTO responseTO = modelMapperFacade.map(metaSaved, MetaResponseTO.class);

        return ResponseEntityFacade.created(responseTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaResponseTO> update(@PathVariable Long id, @Valid @RequestBody MetaRequestTO metaRequestTO) {
        Meta meta = modelMapperFacade.map(metaRequestTO, Meta.class);
        Meta savedMeta = metaService.update(id, meta);
        MetaResponseTO responseTO = modelMapperFacade.map(savedMeta, MetaResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        metaService.deleteById(id);
    }

    @GetMapping("/status")
    public ResponseEntity<List<EnumOptionTO<Status>>> getStatusList() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(Status.values()));
    }

}
