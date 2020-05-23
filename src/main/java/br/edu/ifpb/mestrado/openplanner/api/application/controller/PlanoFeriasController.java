package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.PlanoFerias;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.PlanoFeriasService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias.PlanoFeriasReducedResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias.PlanoFeriasRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.planoferias.PlanoFeriasResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.EnumOptionTO;

@RestController
@RequestMapping("/planos-ferias")
public class PlanoFeriasController {

    private PlanoFeriasService planoFeriasService;

    private ModelMapperFacade modelMapperFacade;

    public PlanoFeriasController(PlanoFeriasService planoFeriasService, ModelMapperFacade modelMapperFacade) {
        super();
        this.planoFeriasService = planoFeriasService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<PlanoFeriasReducedResponseTO>> find(Pageable pageable) {
        Page<PlanoFerias> planoFeriasPage = planoFeriasService.findAll(pageable);
        Page<PlanoFeriasReducedResponseTO> responseTO = modelMapperFacade.map(planoFeriasPage, PlanoFeriasReducedResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoFeriasResponseTO> findById(@PathVariable Long id) {
        PlanoFerias planoFerias = planoFeriasService.findById(id);
        PlanoFeriasResponseTO responseTO = modelMapperFacade.map(planoFerias, PlanoFeriasResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PostMapping
    public ResponseEntity<PlanoFeriasResponseTO> save(@Valid @RequestBody PlanoFeriasRequestTO planoFeriasRequestTO) {
        PlanoFerias planoFerias = modelMapperFacade.map(planoFeriasRequestTO, PlanoFerias.class);
        PlanoFerias planoFeriasSaved = planoFeriasService.save(planoFerias);
        PlanoFeriasResponseTO responseTO = modelMapperFacade.map(planoFeriasSaved, PlanoFeriasResponseTO.class);

        return ResponseEntityFacade.created(responseTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoFeriasResponseTO> update(@PathVariable Long id,
            @Valid @RequestBody PlanoFeriasRequestTO planoFeriasRequestTO) {
        PlanoFerias planoFerias = modelMapperFacade.map(planoFeriasRequestTO, PlanoFerias.class);
        PlanoFerias planoFeriasUpdated = planoFeriasService.update(id, planoFerias);
        PlanoFeriasResponseTO responseTO = modelMapperFacade.map(planoFeriasUpdated, PlanoFeriasResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        planoFeriasService.deleteById(id);
    }

    @GetMapping("/status")
    public ResponseEntity<List<EnumOptionTO<Status>>> getStatus() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(Status.values()));
    }

}
