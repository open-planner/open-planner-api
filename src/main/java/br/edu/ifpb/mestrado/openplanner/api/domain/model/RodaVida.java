package br.edu.ifpb.mestrado.openplanner.api.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseEntity;

@Entity
@Table(name = "roda_vida", schema = "planner")
public class RodaVida extends AuditedBaseEntity implements Serializable {

    private static final long serialVersionUID = -1723760582908883735L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", unique = true, nullable = false)
    private Usuario usuario;

    @NotNull
    @Column(name = "espiritualidade", precision = 3, scale = 2, nullable = false)
    private BigDecimal espiritualidade;

    @NotNull
    @Column(name = "entretenimento", precision = 3, scale = 2, nullable = false)
    private BigDecimal entretenimento;

    @NotNull
    @Column(name = "dinheiro", precision = 3, scale = 2, nullable = false)
    private BigDecimal dinheiro;

    @NotNull
    @Column(name = "carreira", precision = 3, scale = 2, nullable = false)
    private BigDecimal carreira;

    @NotNull
    @Column(name = "desenvolvimentoPessoal", precision = 3, scale = 2, nullable = false)
    private BigDecimal desenvolvimentoPessoal;

    @NotNull
    @Column(name = "relacionamento", precision = 3, scale = 2, nullable = false)
    private BigDecimal relacionamento;

    @NotNull
    @Column(name = "saude", precision = 3, scale = 2, nullable = false)
    private BigDecimal saude;

    @NotNull
    @Column(name = "ambiente", precision = 3, scale = 2, nullable = false)
    private BigDecimal ambiente;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getEspiritualidade() {
        return espiritualidade;
    }

    public void setEspiritualidade(BigDecimal espiritualidade) {
        this.espiritualidade = espiritualidade;
    }

    public BigDecimal getEntretenimento() {
        return entretenimento;
    }

    public void setEntretenimento(BigDecimal entretenimento) {
        this.entretenimento = entretenimento;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
    }

    public BigDecimal getCarreira() {
        return carreira;
    }

    public void setCarreira(BigDecimal carreira) {
        this.carreira = carreira;
    }

    public BigDecimal getDesenvolvimentoPessoal() {
        return desenvolvimentoPessoal;
    }

    public void setDesenvolvimentoPessoal(BigDecimal desenvolvimentoPessoal) {
        this.desenvolvimentoPessoal = desenvolvimentoPessoal;
    }

    public BigDecimal getRelacionamento() {
        return relacionamento;
    }

    public void setRelacionamento(BigDecimal relacionamento) {
        this.relacionamento = relacionamento;
    }

    public BigDecimal getSaude() {
        return saude;
    }

    public void setSaude(BigDecimal saude) {
        this.saude = saude;
    }

    public BigDecimal getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(BigDecimal ambiente) {
        this.ambiente = ambiente;
    }

}
