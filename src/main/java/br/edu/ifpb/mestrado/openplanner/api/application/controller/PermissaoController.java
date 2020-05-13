package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
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

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT', 'ROLE_ADMIN') and #oauth2.hasScope('read')")
    public ResponseEntity<List<PermissaoResponseTO>> findAllActive() {
        List<Permissao> permissoes = permissaoService.findAllActive();
        List<PermissaoResponseTO> responseTOList = modelMapperFacade.map(permissoes, PermissaoResponseTO.class);

        return ResponseEntityFacade.ok(responseTOList);
    }

}
