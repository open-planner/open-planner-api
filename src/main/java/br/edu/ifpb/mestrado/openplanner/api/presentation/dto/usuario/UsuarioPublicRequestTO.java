package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioPublicRequestTO implements Serializable {

    private static final long serialVersionUID = -7433561693465074511L;

    @NotNull
    @NotBlank
    private String nome;

    private LocalDate dataNascimento;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 64)
    @JsonProperty("senha")
    private String senhaValor;

    public UsuarioPublicRequestTO() {
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

    public String getSenhaValor() {
        return senhaValor;
    }

    public void setSenhaValor(String senhaValor) {
        this.senhaValor = senhaValor;
    }

    @Override
    public String toString() {
        return String.format("UsuarioPublicRequestTO [nome=%s, dataNascimento=%s, email=%s, senhaValor=%s]", nome, dataNascimento, email,
                senhaValor);
    }

}
