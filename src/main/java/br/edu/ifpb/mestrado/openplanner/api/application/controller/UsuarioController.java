package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.application.factory.PermissaoLinkFactory;
import br.edu.ifpb.mestrado.openplanner.api.application.factory.UsuarioLinkFactory;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecFactory;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioFilterRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioReducedResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioResponseTO;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    private ModelMapperFacade modelMapperFacade;

    public UsuarioController(UsuarioService usuarioService, ModelMapperFacade modelMapperFacade) {
        this.usuarioService = usuarioService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN') and #oauth2.hasScope('read')")
    @GetMapping
    public ResponseEntity<Page<UsuarioReducedResponseTO>> findAll(UsuarioFilterRequestTO filterRequestTO, Pageable pageable) {
        Specification<Usuario> specification = new SpecFactory<Usuario>().create(filterRequestTO, Usuario.class);
        Page<Usuario> page = usuarioService.findAll(specification, pageable);
        Page<UsuarioReducedResponseTO> responseTOPage = modelMapperFacade.map(page, UsuarioReducedResponseTO.class);

        responseTOPage.getContent().stream().forEach(responseTO -> responseTO.add(UsuarioLinkFactory.create(responseTO.getId())));

        return ResponseEntityFacade.ok(responseTOPage);
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN') and #oauth2.hasScope('read')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseTO> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        UsuarioResponseTO responseTO = modelMapperFacade.map(usuario, UsuarioResponseTO.class);

        addLinks(responseTO);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN') and #oauth2.hasScope('write')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseTO> update(@PathVariable Long id, @RequestBody UsuarioRequestTO requestTO) {
        Usuario usuario = modelMapperFacade.map(requestTO, Usuario.class);
        Usuario updatedUsuario = usuarioService.update(id, usuario);
        UsuarioResponseTO responseTO = modelMapperFacade.map(updatedUsuario, UsuarioResponseTO.class);

        addLinks(responseTO);

        return ResponseEntityFacade.ok(responseTO);
    }

    private void addLinks(UsuarioResponseTO responseTO) {
        responseTO.add(UsuarioLinkFactory.create(responseTO.getId()));
        responseTO.getPermissoes().stream().forEach(p -> p.add(PermissaoLinkFactory.create(p.getId())));
    }

}
