package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.facade.ModelMapperFacade;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic.UsuarioPublicTokenRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic.UsuarioPublicEmailRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic.UsuarioPublicRequestTO;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic.UsuarioPublicSenhaTokenRequestTO;

@RestController
@RequestMapping("/public/usuarios")
public class UsuarioPublicController {

    private UsuarioService usuarioService;

    private ModelMapperFacade modelMapperFacade;

    public UsuarioPublicController(UsuarioService usuarioService, ModelMapperFacade modelMapperFacade) {
        this.usuarioService = usuarioService;
        this.modelMapperFacade = modelMapperFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@Valid @RequestBody UsuarioPublicRequestTO requestTO) {
        Usuario usuario = modelMapperFacade.map(requestTO, Usuario.class);
        usuarioService.save(usuario);
    }

    @PostMapping("/ativacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@RequestBody UsuarioPublicTokenRequestTO requestTO) {
        usuarioService.activate(requestTO.getToken());
    }

    @PostMapping("/atualizacao/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmUpdateEmail(@RequestBody UsuarioPublicTokenRequestTO requestTO) {
        usuarioService.confirmUpdateEmail(requestTO.getToken());
    }

    @PostMapping("/recuperacao/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void recoverSenha(@Valid @RequestBody UsuarioPublicEmailRequestTO requestTO) {
        usuarioService.recoverSenha(requestTO.getEmail());
    }

    @PatchMapping("/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSenhaByResetToken(@RequestBody UsuarioPublicSenhaTokenRequestTO requestTO) {
        usuarioService.updateSenhaByResetToken(requestTO.getToken(), requestTO.getSenha());
    }

}
