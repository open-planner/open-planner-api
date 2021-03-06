package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class UsuarioPublicSenhaTokenRequestTO implements Serializable {

    private static final long serialVersionUID = 1003205023105314425L;

    @NotBlank
    private String token;

    @NotBlank
    private String senha;

    public UsuarioPublicSenhaTokenRequestTO() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return String.format("UsuarioSenhaRequestTO [token=%s, senha=%s]", token, senha);
    }

}
