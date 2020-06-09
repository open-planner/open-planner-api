package br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("open-planner-api.web-app")
public class WebAppProperties {

    private String baseUrl;

    private String mailActivationPath;

    private String updateMailPath;

    private String recoveryPasswordPath;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMailActivationPath() {
        return mailActivationPath;
    }

    public void setMailActivationPath(String mailActivationPath) {
        this.mailActivationPath = mailActivationPath;
    }

    public String getUpdateMailPath() {
        return updateMailPath;
    }

    public void setUpdateMailPath(String updateMailPath) {
        this.updateMailPath = updateMailPath;
    }

    public String getRecoveryPasswordPath() {
        return recoveryPasswordPath;
    }

    public void setRecoveryPasswordPath(String recoveryPasswordPath) {
        this.recoveryPasswordPath = recoveryPasswordPath;
    }

}
