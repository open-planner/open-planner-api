package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecificationFactory;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao.NotificacaoReducedResponseTO;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private NotificacaoService notificacaoService;

    private ModelMapperFacade modelMapperFacade;

    public NotificacaoController(NotificacaoService notificacaoService, ModelMapperFacade modelMapperFacade) {
        super();
        this.notificacaoService = notificacaoService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<Page<NotificacaoReducedResponseTO>> findAll(NotificacaoFilterRequestTO notificacaoFilterRequestTO, Pageable pageable) {
        Specification<Notificacao> specification = new SpecificationFactory<Notificacao>().create(notificacaoFilterRequestTO, Notificacao.class);
        Page<Notificacao> page = notificacaoService.findAll(specification, pageable);
        Page<NotificacaoReducedResponseTO> responseTOPage = modelMapperFacade.map(page, NotificacaoReducedResponseTO.class);

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @PatchMapping("/{id}/lida")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void switchLida(@PathVariable Long id) {
        notificacaoService.switchLida(id);
    }

}
