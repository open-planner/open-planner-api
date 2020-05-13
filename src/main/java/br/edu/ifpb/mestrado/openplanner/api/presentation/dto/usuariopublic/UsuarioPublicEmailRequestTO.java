package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuariopublic;

import java.io.Serializable;

import javax.validation.constraints.Email;

public class UsuarioPublicEmailRequestTO implements Serializable {

    private static final long serialVersionUID = 3538601138000055055L;

    @Email
    private String email;

    public UsuarioPublicEmailRequestTO() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("UsuarioEmailRequestTO [email=%s]", email);
    }

}
