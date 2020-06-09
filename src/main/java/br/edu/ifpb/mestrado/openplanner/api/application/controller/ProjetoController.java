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
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Prioridade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Projeto;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.ProjetoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecBuilder;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto.ProjetoFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto.ProjetoReducedResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto.ProjetoRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto.ProjetoResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.EnumOptionTO;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private ProjetoService projetoService;

    private ModelMapperFacade modelMapperFacade;

    public ProjetoController(ProjetoService projetoService, ModelMapperFacade modelMapperFacade) {
        super();
        this.projetoService = projetoService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<ProjetoReducedResponseTO>> findAll(ProjetoFilterRequestTO projetoFilterRequestTO, Pageable pageable) {
        Specification<Projeto> specification = new SpecBuilder<Projeto>().add(projetoFilterRequestTO).build();
        Page<Projeto> page = projetoService.findAll(specification, pageable);
        Page<ProjetoReducedResponseTO> responseTOPage = modelMapperFacade.map(page, ProjetoReducedResponseTO.class);

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseTO> findById(@PathVariable Long id) {
        Projeto projeto = projetoService.findById(id);
        ProjetoResponseTO responseTO = modelMapperFacade.map(projeto, ProjetoResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PostMapping
    public ResponseEntity<ProjetoResponseTO> save(@Valid @RequestBody ProjetoRequestTO projetoRequestTO) {
        Projeto projeto = modelMapperFacade.map(projetoRequestTO, Projeto.class);
        Projeto projetoSaved = projetoService.save(projeto);
        ProjetoResponseTO responseTO = modelMapperFacade.map(projetoSaved, ProjetoResponseTO.class);

        return ResponseEntityFacade.created(responseTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseTO> update(@PathVariable Long id, @Valid @RequestBody ProjetoRequestTO projetoRequestTO) {
        Projeto projeto = modelMapperFacade.map(projetoRequestTO, Projeto.class);
        Projeto savedProjeto = projetoService.update(id, projeto);
        ProjetoResponseTO responseTO = modelMapperFacade.map(savedProjeto, ProjetoResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        projetoService.deleteById(id);
    }

    @GetMapping("/prioridades")
    public ResponseEntity<List<EnumOptionTO<Prioridade>>> getPrioridadeList() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(Prioridade.values()));
    }

    @GetMapping("/status")
    public ResponseEntity<List<EnumOptionTO<Status>>> getStatusList() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(Status.values()));
    }

}
