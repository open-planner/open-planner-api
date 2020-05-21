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
import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.EventoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecificationFactory;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento.EventoFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento.EventoRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento.EventoResponseTO;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private EventoService eventoService;

    private ModelMapperFacade modelMapperFacade;

    public EventoController(EventoService eventoService, ModelMapperFacade modelMapperFacade) {
        super();
        this.eventoService = eventoService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<EventoResponseTO>> findAll(EventoFilterRequestTO eventoFilterRequestTO, Pageable pageable) {
        Specification<Evento> specification = new SpecificationFactory<Evento>().create(eventoFilterRequestTO, Evento.class);
        Page<Evento> page = eventoService.findAll(specification, pageable);
        Page<EventoResponseTO> responseTOPage = modelMapperFacade.map(page, EventoResponseTO.class);

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseTO> findById(@PathVariable Long id) {
        Evento evento = eventoService.findById(id);
        EventoResponseTO responseTO = modelMapperFacade.map(evento, EventoResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PostMapping
    public ResponseEntity<EventoResponseTO> save(@Valid @RequestBody EventoRequestTO eventoRequestTO) {
        Evento evento = modelMapperFacade.map(eventoRequestTO, Evento.class);
        Evento eventoSaved = eventoService.save(evento);
        EventoResponseTO responseTO = modelMapperFacade.map(eventoSaved, EventoResponseTO.class);

        return ResponseEntityFacade.created(responseTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseTO> update(@PathVariable Long id, @Valid @RequestBody EventoRequestTO eventoRequestTO) {
        Evento evento = modelMapperFacade.map(eventoRequestTO, Evento.class);
        Evento savedEvento = eventoService.update(id, evento);
        EventoResponseTO responseTO = modelMapperFacade.map(savedEvento, EventoResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        eventoService.deleteById(id);
    }

}
