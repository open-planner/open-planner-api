package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.application.factory.PermissaoLinkFactory;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.PermissaoService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.permissao.PermissaoResponseTO;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    private PermissaoService permissaoService;

    private ModelMapperFacade modelMapperFacade;

    public PermissaoController(PermissaoService permissaoService, ModelMapperFacade modelMapperFacade) {
        this.permissaoService = permissaoService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN') and #oauth2.hasScope('read')")
    @GetMapping
    public ResponseEntity<List<PermissaoResponseTO>> findAll() {
        List<Permissao> permissoes = permissaoService.findAll();
        List<PermissaoResponseTO> responseTOList = modelMapperFacade.map(permissoes, PermissaoResponseTO.class);

        responseTOList.stream().forEach(p -> p.add(PermissaoLinkFactory.create(p.getId())));

        return ResponseEntityFacade.ok(responseTOList);
    }

}
