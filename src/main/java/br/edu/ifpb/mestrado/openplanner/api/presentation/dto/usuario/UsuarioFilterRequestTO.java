package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.specification.SpecificationField;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;

public class UsuarioFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 7902233005816840462L;

    private Long id;

    @SpecificationField(property = "id")
    private Set<Long> ids;

    @SpecificationField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
    private String nome;

    @SpecificationField
    private LocalDate dataNascimento;

    @SpecificationField(operation = Operation.LIKE_IGNORE_CASE)
    private String email;

    private Boolean ativo;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return String.format("UsuarioFilterRequestTO [id=%s, nome=%s, dataNascimento=%s, email=%s, ativo=%s]", id, nome, dataNascimento,
                email, ativo);
    }

}
