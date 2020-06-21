package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.github.fagnerlima.springspecificationtools.SpecOperation;
import com.github.fagnerlima.springspecificationtools.annotation.SpecEntity;
import com.github.fagnerlima.springspecificationtools.annotation.SpecField;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;

@SpecEntity(Usuario.class)
public class UsuarioFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 7902233005816840462L;

    private Long id;

    @SpecField(value = "id")
    private Set<Long> ids;

    @SpecField(operation = SpecOperation.LIKE_IGNORE_CASE_UNACCENT)
    private String nome;

    @SpecField
    private LocalDate dataNascimento;

    @SpecField(operation = SpecOperation.LIKE_IGNORE_CASE)
    private String email;

    public UsuarioFilterRequestTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
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
        return String.format("UsuarioFilterRequestTO [id=%s, nome=%s, dataNascimento=%s, email=%s]", id, nome, dataNascimento,
                email);
    }

}
