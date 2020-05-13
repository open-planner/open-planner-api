package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic;

import java.io.Serializable;

public class UsuarioPublicAtivacaoRequestTO implements Serializable {

    private static final long serialVersionUID = 7738381374517418728L;

    private String token;

    public UsuarioPublicAtivacaoRequestTO() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format("UsuarioPublicAtivacaoRequestTO [token=%s]", token);
    }

}
