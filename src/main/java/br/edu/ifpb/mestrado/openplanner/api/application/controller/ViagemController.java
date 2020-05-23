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
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Tipo;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem.Viagem;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.ViagemService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecificationFactory;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.EnumOptionTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem.ViagemFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem.ViagemReducedResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem.ViagemRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem.ViagemResponseTO;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

    private ViagemService viagemService;

    private ModelMapperFacade modelMapperFacade;

    public ViagemController(ViagemService viagemService, ModelMapperFacade modelMapperFacade) {
        super();
        this.viagemService = viagemService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<ViagemReducedResponseTO>> findAll(ViagemFilterRequestTO viagemFilterRequestTO, Pageable pageable) {
        Specification<Viagem> specification = new SpecificationFactory<Viagem>().create(viagemFilterRequestTO, Viagem.class);
        Page<Viagem> viagensPage = viagemService.findAll(specification, pageable);
        Page<ViagemReducedResponseTO> responseTOPage = modelMapperFacade.map(viagensPage, ViagemReducedResponseTO.class);

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViagemResponseTO> findById(@PathVariable Long id) {
        Viagem viagem = viagemService.findById(id);
        ViagemResponseTO responseTO = modelMapperFacade.map(viagem, ViagemResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PostMapping
    public ResponseEntity<ViagemResponseTO> save(@Valid @RequestBody ViagemRequestTO viagemRequestTO) {
        Viagem viagem = modelMapperFacade.map(viagemRequestTO, Viagem.class);
        Viagem viagemSaved = viagemService.save(viagem);
        ViagemResponseTO responseTO = modelMapperFacade.map(viagemSaved, ViagemResponseTO.class);

        return ResponseEntityFacade.created(responseTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViagemResponseTO> update(@PathVariable Long id, @Valid @RequestBody ViagemRequestTO viagemRequestTO) {
        Viagem viagem = modelMapperFacade.map(viagemRequestTO, Viagem.class);
        Viagem viagemUpdated = viagemService.update(id, viagem);
        ViagemResponseTO responseTO = modelMapperFacade.map(viagemUpdated, ViagemResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        viagemService.deleteById(id);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<EnumOptionTO<Tipo>>> getTipos() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(Tipo.values()));
    }

    @GetMapping("/status")
    public ResponseEntity<List<EnumOptionTO<Status>>> getStatus() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(Status.values()));
    }

}
