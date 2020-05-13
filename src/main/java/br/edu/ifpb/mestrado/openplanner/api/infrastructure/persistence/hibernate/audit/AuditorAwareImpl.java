package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

public class AuditorAwareImpl implements AuditorAware<String> {

    private OAuth2UserDetailsService userDetailsService;

    public AuditorAwareImpl(OAuth2UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(userDetailsService.isAuthenticated()
                ? userDetailsService.getUsernameAuth()
                : userDetailsService.getAnonymousUsername());
    }

}
