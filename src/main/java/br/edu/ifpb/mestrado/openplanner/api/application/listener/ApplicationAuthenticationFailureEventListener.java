package br.edu.ifpb.mestrado.openplanner.api.application.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;

@Component
public class ApplicationAuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private UsuarioService usuarioService;

    public ApplicationAuthenticationFailureEventListener(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        usuarioService.loginFailed((String) event.getAuthentication().getPrincipal());
    }

}
