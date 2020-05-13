package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuarioautenticado;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

public class UsuarioAutenticadoResponseTO extends RepresentationModel<UsuarioAutenticadoResponseTO> implements Serializable {

    private static final long serialVersionUID = -2629152547405312072L;

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    public UsuarioAutenticadoResponseTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return String.format("UsuarioAutenticadoResponseTO [id=%s, nome=%s, dataNascimento=%s, email=%s]", id, nome, dataNascimento, email);
    }
}
