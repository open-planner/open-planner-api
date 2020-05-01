package br.edu.ifpb.mestrado.openplanner.api.application.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.auth.UsuarioAuth;

@Component
public class ApplicationAuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private UsuarioService usuarioService;

    public ApplicationAuthenticationSuccessEventListener(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if (event.getAuthentication().getPrincipal() instanceof UsuarioAuth) {
            usuarioService.loginSucceded(((UsuarioAuth) event.getAuthentication().getPrincipal()).getUsername());
        }
    }

}
