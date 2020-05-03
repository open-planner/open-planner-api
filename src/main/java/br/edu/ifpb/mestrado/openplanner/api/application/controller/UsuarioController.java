package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.application.factory.GrupoLinkFactory;
import br.edu.ifpb.mestrado.openplanner.api.application.factory.UsuarioLinkFactory;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecificationFactory;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioMinResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioReducedResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioResponseTO;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    private ModelMapperFacade converterService;

    public UsuarioController(UsuarioService usuarioService, ModelMapperFacade converterService) {
        this.usuarioService = usuarioService;
        this.converterService = converterService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT', 'ROLE_ADMIN') and #oauth2.hasScope('read')")
    @GetMapping
    public ResponseEntity<Page<UsuarioReducedResponseTO>> findAll(UsuarioFilterRequestTO filterRequestTO, Pageable pageable) {
        Specification<Usuario> specification = new SpecificationFactory<Usuario>().create(filterRequestTO, Usuario.class);
        Page<Usuario> page = usuarioService.findAll(specification, pageable);
        Page<UsuarioReducedResponseTO> responseTOPage = converterService.map(page, UsuarioReducedResponseTO.class);

        responseTOPage.getContent().stream().forEach(responseTO -> responseTO.add(UsuarioLinkFactory.create(responseTO.getId())));

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT', 'ROLE_ADMIN') and #oauth2.hasScope('read')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseTO> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        UsuarioResponseTO responseTO = converterService.map(usuario, UsuarioResponseTO.class);

        addLinks(responseTO);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT', 'ROLE_ADMIN') and #oauth2.hasScope('read')")
    @GetMapping("/ativos")
    public ResponseEntity<List<UsuarioMinResponseTO>> findAllActive() {
        List<Usuario> usuarios = usuarioService.findAllActive();
        List<UsuarioMinResponseTO> responseTOList = converterService.map(usuarios, UsuarioMinResponseTO.class);

        responseTOList.stream().forEach(responseTO -> responseTO.add(UsuarioLinkFactory.create(responseTO.getId())));

        return ResponseEntityFacade.ok(responseTOList);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT', 'ROLE_ADMIN') and #oauth2.hasScope('write')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseTO> update(@PathVariable Long id, @RequestBody UsuarioRequestTO requestTO) {
        Usuario usuario = converterService.map(requestTO, Usuario.class);
        Usuario updatedUsuario = usuarioService.update(id, usuario);
        UsuarioResponseTO responseTO = converterService.map(updatedUsuario, UsuarioResponseTO.class);

        addLinks(responseTO);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ROOT', 'ROLE_ADMIN') and #oauth2.hasScope('write')")
    @PatchMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void switchActive(@PathVariable Long id) {
        usuarioService.switchActive(id);
    }

    private void addLinks(UsuarioResponseTO responseTO) {
        responseTO.add(UsuarioLinkFactory.create(responseTO.getId()));
        responseTO.getGrupos().stream().forEach(grupo -> grupo.add(GrupoLinkFactory.create(grupo.getId())));
    }

}
