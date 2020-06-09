package br.edu.ifpb.mestrado.openplanner.api.infrastructure.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties.WebAppProperties;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.email.MailRequestTO;

@Component
public class MailFactory {

    private final String TEMPLATE_REGISTRATION_USUARIO = "mail/usuario-registration";
    private final String SUBJECT_REGISTRATION_USUARIO = "Cadastro de Usuário";

    private final String TEMPLATE_UPDATE_USUARIO_EMAIL = "mail/usuario-update-email";
    private final String SUBJECT_UPDATE_USUARIO_EMAIL = "Alteração de E-mail do Usuário";

    private final String TEMPLATE_UPDATE_USUARIO_SENHA = "mail/usuario-update-senha";
    private final String SUBJECT_UPDATE_USUARIO_SENHA = "Alteração de Senha do Usuário";

    private final String TEMPLATE_RECOVERY_USUARIO_SENHA = "mail/usuario-recovery-senha";
    private final String SUBJECT_RECOVERY_USUARIO_SENHA = "Recuperação de Senha";

    private final String TEMPLATE_NOTIFICACAO = "mail/notificacoes";
    private final String SUBJECT_NOTIFICACAO = "Notificações";

    private TemplateEngine templateEngine;

    private WebAppProperties webAppProperties;

    public MailFactory(TemplateEngine templateEngine, WebAppProperties webAppProperties) {
        this.templateEngine = templateEngine;
        this.webAppProperties = webAppProperties;
    }

    public MailRequestTO createRegistrationUsuario(Usuario usuario) {
        Map<String, Object> data = new HashMap<>();
        data.put("webAppMailActivationPath", buildTokenPath(webAppProperties.getMailActivationPath(), usuario.getAtivacaoToken()));

        return createTemplateUsuario(TEMPLATE_REGISTRATION_USUARIO, SUBJECT_REGISTRATION_USUARIO, usuario, data);
    }

    public MailRequestTO createUpdateEmailUsuario(Usuario usuario) {
        Map<String, Object> data = new HashMap<>();
        data.put("webAppUpdateMailPath", buildTokenPath(webAppProperties.getUpdateMailPath(), usuario.getAlteracaoToken()));

        return createTemplateUsuario(TEMPLATE_UPDATE_USUARIO_EMAIL, SUBJECT_UPDATE_USUARIO_EMAIL, usuario, data);
    }

    public MailRequestTO createUpdateSenhaUsuario(Usuario usuario) {
        return createTemplateUsuario(TEMPLATE_UPDATE_USUARIO_SENHA, SUBJECT_UPDATE_USUARIO_SENHA, usuario);
    }

    public MailRequestTO createRecoveryUsuarioSenha(Usuario usuario) {
        Map<String, Object> data = new HashMap<>();
        data.put("webAppRecoveryPasswordPath",
                buildTokenPath(webAppProperties.getRecoveryPasswordPath(), usuario.getSenha().getResetToken()));

        return createTemplateUsuario(TEMPLATE_RECOVERY_USUARIO_SENHA, SUBJECT_RECOVERY_USUARIO_SENHA, usuario, data);
    }

    public MailRequestTO createNotificacoes(List<Notificacao> notificacoes) {
        Usuario usuario = notificacoes.get(0).getUsuario();
        Map<String, Object> data = new HashMap<>();
        data.put("usuario", usuario);
        data.put("notificacoes", notificacoes);

        return new MailRequestTO(usuario.getEmail(), SUBJECT_NOTIFICACAO, createText(TEMPLATE_NOTIFICACAO, data));
    }

    private MailRequestTO createTemplateUsuario(String template, String subject, Usuario usuario, Map<String, Object> data) {
        data.put("usuario", usuario);

        return new MailRequestTO(usuario.getEmail(), subject, createText(template, data));
    }

    private MailRequestTO createTemplateUsuario(String template, String subject, Usuario usuario) {
        return createTemplateUsuario(template, subject, usuario, new HashMap<>());
    }

    private String createText(String template, Map<String, Object> data) {
        Context context = new Context(new Locale("pt", "BR"));
        data.put("webAppBaseUrl", webAppProperties.getBaseUrl());
        data.entrySet().forEach(d -> context.setVariable(d.getKey(), d.getValue()));

        return templateEngine.process(template, context);
    }

    private String buildTokenPath(String path, String token) {
        return path.replace("{token}", token);
    }

}
