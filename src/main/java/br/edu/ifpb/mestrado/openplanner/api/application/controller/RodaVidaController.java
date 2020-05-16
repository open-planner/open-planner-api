package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.rodavida.RodaVida;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.RodaVidaService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.rodavida.RodaVidaRequesTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.rodavida.RodaVidaResponseTO;

@RestController
@RequestMapping("/roda-vida")
public class RodaVidaController {

    private RodaVidaService rodaVidaService;

    private ModelMapperFacade modelMapperFacade;

    public RodaVidaController(RodaVidaService rodaVidaService, ModelMapperFacade modelMapperFacade) {
        this.rodaVidaService = rodaVidaService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<RodaVidaResponseTO> find() {
        RodaVida rodaVida = rodaVidaService.findByUsuarioAutenticado();
        RodaVidaResponseTO rodaVidaResponseTO = modelMapperFacade.map(rodaVida, RodaVidaResponseTO.class);

        return ResponseEntityFacade.ok(rodaVidaResponseTO);
    }

    @PutMapping
    public ResponseEntity<RodaVidaResponseTO> update(@Valid @RequestBody RodaVidaRequesTO rodaVidaRequestTO) {
        RodaVida rodaVida = modelMapperFacade.map(rodaVidaRequestTO, RodaVida.class);
        RodaVida updatedRodaVida = rodaVidaService.updateByUsuarioAutenticado(rodaVida);
        RodaVidaResponseTO rodaVidaResponseTO = modelMapperFacade.map(updatedRodaVida, RodaVidaResponseTO.class);

        return ResponseEntityFacade.ok(rodaVidaResponseTO);
    }

}
