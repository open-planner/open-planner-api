package br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.bean.Nullable;

@Entity
@Table(name = "usuario", schema = "auth")
public class Usuario extends AuditedBaseEntity {

    private static final long serialVersionUID = 4992180182377301896L;

    public static final Long ID_ROOT = -1L;
    public static final Long ID_SYSTEM = -2L;
    public static final Long ID_ADMIN = 1L;

    @NotNull
    @Size(min = 5, max = 128)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Size(min = 5, max = 128)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Valid
    @Embedded
    private Senha senha;

    @Nullable
    @Column(name = "ativacao_token")
    private String ativacaoToken;

    @NotNull
    @Column(name = "pendente", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean pendente;

    @NotNull
    @Column(name = "bloqueado", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean bloqueado;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "usuario_permissao",
            schema = "auth",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_permissao"))
    private Set<Permissao> permissoes;

    public Usuario() {
        super();
    }

    public Boolean isRoot() {
        return id != null && id.equals(ID_ROOT);
    }

    public Boolean isSystem() {
        return id != null && id.equals(ID_SYSTEM);
    }

    public Boolean isAdmin() {
        return id != null && id.equals(ID_ADMIN);
    }

    public void generateAtivacaoToken() {
        ativacaoToken = UUID.randomUUID().toString();
    }

    public Boolean hasSenha() {
        return senha != null && senha.getValor() != null && !senha.getValor().isBlank();
    }

    public void updateSenha(String valorSenha) {
        senha = new Senha(valorSenha);
    }

    public Boolean anyPermissaoMatch(Predicate<Permissao> predicate) {
        return permissoes != null && !permissoes.isEmpty() && permissoes.stream().anyMatch(predicate);
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

    public Senha getSenha() {
        return senha;
    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    public String getAtivacaoToken() {
        return ativacaoToken;
    }

    public void setAtivacaoToken(String ativacaoToken) {
        this.ativacaoToken = ativacaoToken;
    }

    public Boolean getPendente() {
        return pendente;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

}
