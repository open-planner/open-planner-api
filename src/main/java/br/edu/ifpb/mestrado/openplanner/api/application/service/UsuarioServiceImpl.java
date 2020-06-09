package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties.OpenPlannerApiProperties;
import br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties.SecurityProperties;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.BusinessException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.DuplicateKeyException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InvalidActualPasswordException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InvalidTokenException;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.NotAuthenticatedUserException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.factory.MailFactory;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.UsuarioRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.util.BcryptUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.service.MailService;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario> implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    private MailService mailService;

    private MailFactory mailFactory;

    private SecurityProperties securityProperties;

    private OpenPlannerApiProperties openPlannerApiProperties;

    public UsuarioServiceImpl(OAuth2UserDetailsService userDetailsService, UsuarioRepository usuarioRepository, MailService mailService,
            MailFactory mailFactory, SecurityProperties securityProperties, OpenPlannerApiProperties openPlannerApiProperties) {
        super(userDetailsService);
        this.usuarioRepository = usuarioRepository;
        this.mailService = mailService;
        this.mailFactory = mailFactory;
        this.securityProperties = securityProperties;
        this.openPlannerApiProperties = openPlannerApiProperties;
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new InformationNotFoundException());
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findByAtivacaoToken(String token) {
        return usuarioRepository.findByAtivacaoToken(token)
                .orElseThrow(() -> new InformationNotFoundException());
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findByAlteracaoToken(String token) {
        return usuarioRepository.findByAlteracaoToken(token)
                .orElseThrow(() -> new InformationNotFoundException());
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findBySenhaResetToken(String resetToken) {
        return usuarioRepository.findBySenhaResetToken(resetToken).orElseThrow(() -> new InvalidTokenException());
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario getAutenticado() {
        try {
            return getUsuarioAutenticado();
        } catch (Exception exception) {
            throw new NotAuthenticatedUserException();
        }
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        checkSave(usuario);

        usuario.setEmail(usuario.getEmail().toLowerCase());
        usuario.updateSenha(BcryptUtils.encode(usuario.getSenha().getValor()));
        usuario.setPendente(true);
        usuario.setBloqueado(false);
        usuario.generateAtivacaoToken();

        Usuario usuarioSaved = super.save(usuario);

        sendMailRegistration(usuarioSaved);

        return usuarioSaved;
    }

    @Transactional
    @Override
    public void activate(String token) {
        Usuario usuario = findByAtivacaoToken(token);
        usuario.setAtivacaoToken(null);
        usuario.setPendente(false);

        super.save(usuario);
    }

    @Transactional
    @Override
    public void confirmUpdateEmail(String token) {
        Usuario usuario = findByAlteracaoToken(token);
        usuario.setEmail(usuario.getEmailAlterado());
        usuario.setEmailAlterado(null);
        usuario.setAlteracaoToken(null);
        usuario.setPendente(true);
        usuario.generateAtivacaoToken();

        Usuario usuarioUpdated = super.save(usuario);

        sendMailRegistration(usuarioUpdated);
    }

    @Transactional
    @Override
    public Usuario update(Long id, Usuario usuario) {
        Boolean resendMailRegistration = false;
        Usuario usuarioSaved = findById(id);

        checkUpdate(id, usuario, usuarioSaved);

        if (!usuario.getEmail().equalsIgnoreCase(usuarioSaved.getEmail())) {
            if (!usuarioSaved.getPendente()) {
                usuario.setEmailAlterado(usuario.getEmail());
                usuario.setEmail(usuarioSaved.getEmail());
                usuario.generateAlteracaoToken();
            } else {
                usuario.generateAtivacaoToken();
                resendMailRegistration = true;
            }
        }

        usuario.setSenha(usuarioSaved.getSenha());

        Usuario usuarioUpdated = super.update(id, usuario);

        if (resendMailRegistration) {
            sendMailRegistration(usuarioUpdated);
        } else if (usuarioUpdated.getEmailAlterado() != null) {
            sendMailUpdateEmail(usuarioUpdated);
        }

        return usuarioUpdated;
    }

    @Transactional
    @Override
    public Usuario updateAutenticado(Usuario usuario) {
        Usuario usuarioAutenticado = getUserDetailsService().getUsuarioAuth().getUsuario();
        usuario.setPermissoes(usuarioAutenticado.getPermissoes());

        return update(usuarioAutenticado.getId(), usuario);
    }

    @Transactional
    @Override
    public Usuario updateSenhaByResetToken(String resetToken, String senha) {
        Usuario usuario = findBySenhaResetToken(resetToken);
        usuario.updateSenha(BcryptUtils.encode(senha));
        usuario.setPendente(false);
        usuario.setBloqueado(false);

        Usuario usuarioUpdated = super.update(usuario.getId(), usuario);

        sendMailUpdateSenha(usuarioUpdated);

        return usuarioUpdated;
    }

    @Transactional
    @Override
    public Usuario updateSenhaAutenticado(String senhaAtual, String senhaNova) {
        Usuario usuario = getAutenticado();

        if (!BcryptUtils.validate(senhaAtual, usuario.getSenha().getValor())) {
            throw new InvalidActualPasswordException();
        }

        usuario.updateSenha(BcryptUtils.encode(senhaNova));

        Usuario usuarioUpdated = super.update(usuario.getId(), usuario);

        sendMailUpdateSenha(usuarioUpdated);

        return usuarioUpdated;
    }

    @Transactional
    @Override
    public void recoverSenha(String email) {
        Usuario usuario = findByEmail(email);
        usuario.getSenha().generateResetToken();

        Usuario usuarioUpdated = super.update(usuario.getId(), usuario);

        sendMailRecoverySenha(usuarioUpdated);
    }

    @Transactional
    @Override
    public void loginFailed(String email) {
        try {
            Usuario usuario = findByEmail(email);
            usuario.getSenha().addTentativaErro();

            if (usuario.getSenha().getTentativasErro() >= securityProperties.getMaximumAttemptsLogin()) {
                usuario.setBloqueado(true);
            }

            super.update(usuario.getId(), usuario);
        } catch (InformationNotFoundException informationNotFoundException) {
        }
    }

    @Transactional
    @Override
    public void loginSucceded(String email) {
        Usuario usuario = findByEmail(email);

        if (usuario.getSenha().getTentativasErro() == 0) {
            return;
        }

        usuario.getSenha().resetTentativasErro();

        super.update(usuario.getId(), usuario);
    }

    @Override
    protected BaseRepository<Usuario> getRepository() {
        return usuarioRepository;
    }

    private void checkSave(Usuario usuario) {
        checkUniqueFields(usuario);
        checkDataNascimento(usuario);

        if (usuario.getPermissoes() == null || usuario.getPermissoes().isEmpty()) {
            return;
        }

        if (usuario.anyPermissaoMatch(Permissao::isRoot)) {
            throw new BusinessException("usuario.save.permissoes.root");
        }

        if (usuario.anyPermissaoMatch(Permissao::isSystem)) {
            throw new BusinessException("usuario.save.permissoes.system");
        }

        if (!getAutenticado().isAdmin() && !getAutenticado().isRoot() && usuario.anyPermissaoMatch(Permissao::isAdmin)) {
            throw new BusinessException("usuario.save.permissoes.admin");
        }
    }

    private void checkUpdate(Long id, Usuario usuario, Usuario usuarioSaved) {
        if (!usuario.getEmail().equalsIgnoreCase(usuarioSaved.getEmail())) {
            checkUniqueEmail(usuario);
            usuario.setEmail(usuario.getEmail().toLowerCase());
        }

        checkDataNascimento(usuario);

        if (id == Usuario.ID_ROOT) {
            checkUpdateRoot(usuario);
        }

        if (id == Usuario.ID_SYSTEM) {
            checkUpdateSystem(usuario);
        }

        if (id == Usuario.ID_ADMIN) {
            checkUpdateAdmin(usuario);
        }
    }

    private void checkDataNascimento(Usuario usuario) {
        Integer minimumAge = openPlannerApiProperties.getUsuario().getMinimumAge();

        if (usuario.getDataNascimento().isAfter(LocalDate.now().minusYears(minimumAge))) {
            throw new BusinessException("usuario.minimum-age", minimumAge.toString());
        }
    }

    private void checkUpdateRoot(Usuario usuario) {
        if (!usuario.anyPermissaoMatch(Permissao::isRoot)) {
            throw new BusinessException("usuario.update.permissoes.root");
        }
    }

    private void checkUpdateSystem(Usuario usuario) {
        if (!usuario.anyPermissaoMatch(Permissao::isSystem)) {
            throw new BusinessException("usuario.update.permissoes.system");
        }
    }

    private void checkUpdateAdmin(Usuario usuario) {
        if (!usuario.anyPermissaoMatch(Permissao::isAdmin)) {
            throw new BusinessException("usuario.update.permissoes.admin");
        }
    }

    private void checkUniqueFields(Usuario usuario) {
        checkUniqueEmail(usuario);
    }

    @Transactional(readOnly = true)
    private void checkUniqueEmail(Usuario usuario) {
        usuarioRepository.findByEmailIgnoreCase(usuario.getEmail()).ifPresent(u -> {
            throw new DuplicateKeyException("usuario.duplicate-key.email");
        });
    }

    private void sendMailRegistration(Usuario usuario) {
        mailService.send(mailFactory.createRegistrationUsuario(usuario));
    }

    private void sendMailUpdateEmail(Usuario usuario) {
        mailService.send(mailFactory.createUpdateEmailUsuario(usuario));
    }

    private void sendMailUpdateSenha(Usuario usuario) {
        mailService.send(mailFactory.createUpdateSenhaUsuario(usuario));
    }

    private void sendMailRecoverySenha(Usuario usuario) {
        mailService.send(mailFactory.createRecoveryUsuarioSenha(usuario));
    }

}
