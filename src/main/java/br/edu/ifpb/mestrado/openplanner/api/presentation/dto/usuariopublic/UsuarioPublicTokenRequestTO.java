package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class UsuarioPublicTokenRequestTO implements Serializable {

    private static final long serialVersionUID = 7738381374517418728L;

    @NotBlank
    private String token;

    public UsuarioPublicTokenRequestTO() {
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
        return String.format("UsuarioPublicTokenRequestTO [token=%s]", token);
    }

}
