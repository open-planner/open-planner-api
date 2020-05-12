package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.application.facade.ResponseEntityFacade;
import br.edu.ifpb.mestrado.openplanner.api.application.factory.UsuarioLinkFactory;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioAutenticadoRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioAutenticadoResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioAutenticadoSenhaRequestTO;

@RestController
@RequestMapping("/me")
public class UsuarioAutenticadoController {

    private UsuarioService usuarioService;

    private ModelMapperFacade modelMapperFacade;

    public UsuarioAutenticadoController(UsuarioService usuarioService, ModelMapperFacade modelMapperFacade) {
        this.usuarioService = usuarioService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @GetMapping
    public ResponseEntity<UsuarioAutenticadoResponseTO> find() {
        Usuario usuario = usuarioService.getAutenticado();
        UsuarioAutenticadoResponseTO responseTO = modelMapperFacade.map(usuario, UsuarioAutenticadoResponseTO.class);

        addLinks(responseTO);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PutMapping
    public ResponseEntity<UsuarioAutenticadoResponseTO> update(@RequestBody UsuarioAutenticadoRequestTO requestTO) {
        Usuario usuario = modelMapperFacade.map(requestTO, Usuario.class);
        Usuario updatedUsuario = usuarioService.updateAutenticado(usuario);
        UsuarioAutenticadoResponseTO responseTO = modelMapperFacade.map(updatedUsuario, UsuarioAutenticadoResponseTO.class);

        addLinks(responseTO);

        return ResponseEntityFacade.ok(responseTO);
    }

    @PatchMapping("/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSenha(@RequestBody UsuarioAutenticadoSenhaRequestTO requestTO) {
        usuarioService.updateSenhaAutenticado(requestTO.getSenhaAtual(), requestTO.getSenhaNova());
    }

    private void addLinks(UsuarioAutenticadoResponseTO responseTO) {
        responseTO.add(UsuarioLinkFactory.create(responseTO.getId()));
    }

}
