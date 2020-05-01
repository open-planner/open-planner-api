package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.grupo.Grupo;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.converter.IdReference;

public class UsuarioRequestTO implements Serializable {

    private static final long serialVersionUID = -7433561693465074511L;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    @IdReference(target = Grupo.class, property = "grupos")
    private Set<Long> grupos;

    private Boolean ativo;

    public UsuarioRequestTO() {
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

    public Set<Long> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Long> grupos) {
        this.grupos = grupos;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return String.format("UsuarioRequestTO [nome=%s, dataNascimento=%s, email=%s, grupos=%s, ativo=%s]", nome, dataNascimento, email,
                grupos, ativo);
    }

}
