package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.RodaVida;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.RodaVidaService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
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

        return ResponseEntity.ok(rodaVidaResponseTO);
    }

}
