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

    private ModelMapperFacade converterService;

    public PermissaoController(PermissaoService permissaoService, ModelMapperFacade converterService) {
        this.permissaoService = permissaoService;
        this.converterService = converterService;
    }

    @GetMapping("/ativos")
    @PreAuthorize("hasAnyAuthority('ROLE_ROOT', 'ROLE_ADMIN') and #oauth2.hasScope('read')")
    public ResponseEntity<List<PermissaoResponseTO>> findAllActive() {
        List<Permissao> permissoes = permissaoService.findAllActive();
        List<PermissaoResponseTO> responseTOList = converterService.map(permissoes, PermissaoResponseTO.class);

        responseTOList.stream().forEach(responseTO -> responseTO.add(PermissaoLinkFactory.create(responseTO.getId())));

        return ResponseEntityFacade.ok(responseTOList);
    }

}
