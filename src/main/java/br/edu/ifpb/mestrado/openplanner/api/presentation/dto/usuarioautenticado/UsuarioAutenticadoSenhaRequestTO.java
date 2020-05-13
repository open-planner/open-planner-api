package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuarioautenticado;

import java.io.Serializable;

public class UsuarioAutenticadoSenhaRequestTO implements Serializable {

    private static final long serialVersionUID = -2750268280690970610L;

    private String senhaAtual;

    private String senhaNova;

    public UsuarioAutenticadoSenhaRequestTO() {
        super();
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    @Override
    public String toString() {
        return String.format("UsuarioSenhaRequestTO [senhaAtual=%s, senhaNova=%s]", senhaAtual, senhaNova);
    }

}
