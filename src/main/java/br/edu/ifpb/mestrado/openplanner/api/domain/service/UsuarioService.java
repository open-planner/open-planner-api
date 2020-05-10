package br.edu.ifpb.mestrado.openplanner.api.domain.service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;

public interface UsuarioService extends BaseService<Usuario> {

    Usuario activate(String token);

    Usuario findByEmail(String email);

    Usuario findByAtivacaoToken(String token);

    Usuario findBySenhaResetToken(String resetToken);

    Usuario updateSenhaByResetToken(String resetToken, String senha);

    void recoverSenha(String email);

    Usuario getAutenticado();

    Usuario updateAutenticado(Usuario usuario);

    Usuario updateSenhaAutenticado(String senhaAtual, String senhaNova);

    void loginFailed(String login);

    void loginSucceded(String login);

    void switchBloqueado(Long id);

}
