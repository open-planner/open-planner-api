package br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("open-planner-api.security")
public class SecurityProperties {

    private Integer maximumAttemptsLogin;

    public Integer getMaximumAttemptsLogin() {
        return maximumAttemptsLogin;
    }

    public void setMaximumAttemptsLogin(Integer maximumAttemptsLogin) {
        this.maximumAttemptsLogin = maximumAttemptsLogin;
    }

}
