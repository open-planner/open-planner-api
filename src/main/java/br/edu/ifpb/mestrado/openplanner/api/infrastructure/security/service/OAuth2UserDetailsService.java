package br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.UsuarioRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.auth.UsuarioAuth;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.exception.AuthenticationException;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.exception.IncorrectUsernameOrPasswordException;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.exception.UnauthenticatedException;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.service.MessageService;

@Service
public class OAuth2UserDetailsService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    private MessageService messageService;

    public OAuth2UserDetailsService(UsuarioRepository usuarioRepository, MessageService messageService) {
        this.usuarioRepository = usuarioRepository;
        this.messageService = messageService;
    }

    @Override
    public UsuarioAuth loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                    .orElseThrow(() -> new InformationNotFoundException());
            validateUsuario(usuario);

            return new UsuarioAuth(usuario, getAuthorities(usuario));
        } catch (InformationNotFoundException informationNotFoundException) {
            throw new IncorrectUsernameOrPasswordException(messageService.getMessage("security.incorrect-username-or-password"));
        }
    }

    public UsuarioAuth loadUserById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new InformationNotFoundException());
        validateUsuario(usuario);

        return new UsuarioAuth(usuario, getAuthorities(usuario));
    }

    public Boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && authentication.getPrincipal() instanceof String;
    }

    public String getUsernameAuth() {
        if (!isAuthenticated()) {
            throw new UnauthenticatedException();
        }

        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getUsernameSystem() {
        return loadUserById(Usuario.ID_SYSTEM).getUsername();
    }

    public UsuarioAuth getUsuarioAuth() {
        return loadUserByUsername(getUsernameAuth());
    }

    public UsuarioAuth getUsuarioAuth(OAuth2Authentication authentication) {
        return (UsuarioAuth) authentication.getPrincipal();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        usuario.getPermissoes().stream()
                .forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getPapel().name())));

        return authorities;
    }

    private void validateUsuario(Usuario usuario) {
        if (usuario.getExcluded()) {
            throw new AuthenticationException(messageService.getMessage("usuario.excluido"));
        }

        if (usuario.getPendente()) {
            throw new AuthenticationException(messageService.getMessage("usuario.pendente"));
        }

        if (usuario.getBloqueado()) {
            throw new AuthenticationException(messageService.getMessage("usuario.bloqueado"));
        }
    }

}
