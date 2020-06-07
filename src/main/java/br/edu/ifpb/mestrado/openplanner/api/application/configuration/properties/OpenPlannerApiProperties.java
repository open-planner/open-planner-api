package br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("open-planner-api")
public class OpenPlannerApiProperties {

    private UsuarioProperties usuario;

    public UsuarioProperties getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioProperties usuario) {
        this.usuario = usuario;
    }

    public static class UsuarioProperties {
        private Integer minimumAge;

        public Integer getMinimumAge() {
            return minimumAge;
        }

        public void setMinimumAge(Integer minimumAge) {
            this.minimumAge = minimumAge;
        }
    }

}
