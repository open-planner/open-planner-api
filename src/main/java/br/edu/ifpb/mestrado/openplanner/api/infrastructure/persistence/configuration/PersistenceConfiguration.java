package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.audit.AuditorAwareImpl;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfiguration {

    private OAuth2UserDetailsService userDetailsService;

    public PersistenceConfiguration(OAuth2UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl(userDetailsService);
    }
}
