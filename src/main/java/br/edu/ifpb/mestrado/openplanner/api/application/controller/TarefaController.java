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

import com.github.fagnerlima.springspecificationtools.SpecBuilder;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa.Tarefa;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.TarefaService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.RecorrenciaTimeUnit;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.EnumOptionTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa.TarefaFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa.TarefaReducedResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa.TarefaRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa.TarefaResponseTO;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private TarefaService tarefaService;

    private ModelMapperFacade modelMapperFacade;

    public TarefaController(TarefaService tarefaService, ModelMapperFacade modelMapperFacade) {
        super();
        this.tarefaService = tarefaService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<TarefaReducedResponseTO>> findAll(TarefaFilterRequestTO tarefaFilterRequestTO, Pageable pageable) {
        Specification<Tarefa> specification = new SpecBuilder<Tarefa>().add(tarefaFilterRequestTO).build();
        Page<Tarefa> page = tarefaService.findAll(specification, pageable);
        Page<TarefaReducedResponseTO> responseTOPage = modelMapperFacade.map(page, TarefaReducedResponseTO.class);

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseTO> findById(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.findById(id);
        TarefaResponseTO responseTO = modelMapperFacade.map(tarefa, TarefaResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PostMapping
    public ResponseEntity<TarefaResponseTO> save(@Valid @RequestBody TarefaRequestTO tarefaRequestTO) {
        Tarefa tarefa = modelMapperFacade.map(tarefaRequestTO, Tarefa.class);
        Tarefa tarefaSaved = tarefaService.save(tarefa);
        TarefaResponseTO responseTO = modelMapperFacade.map(tarefaSaved, TarefaResponseTO.class);

        return ResponseEntityFacade.created(responseTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseTO> update(@PathVariable Long id, @Valid @RequestBody TarefaRequestTO tarefaRequestTO) {
        Tarefa tarefa = modelMapperFacade.map(tarefaRequestTO, Tarefa.class);
        Tarefa savedTarefa = tarefaService.update(id, tarefa);
        TarefaResponseTO responseTO = modelMapperFacade.map(savedTarefa, TarefaResponseTO.class);

        return ResponseEntityFacade.ok(responseTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tarefaService.deleteById(id);
    }

    @GetMapping("/status")
    public ResponseEntity<List<EnumOptionTO<Status>>> getStatus() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(Status.values()));
    }

    @GetMapping("/recorrencias/time-units")
    public ResponseEntity<List<EnumOptionTO<RecorrenciaTimeUnit>>> getRecorrenciaTimeUnitList() {
        return ResponseEntityFacade.ok(modelMapperFacade.map(RecorrenciaTimeUnit.values()));
    }

}
