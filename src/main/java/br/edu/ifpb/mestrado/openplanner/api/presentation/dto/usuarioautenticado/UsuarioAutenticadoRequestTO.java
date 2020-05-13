package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuarioautenticado;

import java.io.Serializable;
import java.time.LocalDate;

public class UsuarioAutenticadoRequestTO implements Serializable {

    private static final long serialVersionUID = -4976627326375118702L;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    public UsuarioAutenticadoRequestTO() {
        super();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("UsuarioRequestTO [nome=%s, email=%s]", nome, email);
    }

}
