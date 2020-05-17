package br.edu.ifpb.mestrado.openplanner.api.domain.model.rodavida;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.AuditedBaseOneByUsuarioEntity;

@Entity
@Table(name = "roda_vida", schema = "planner")
public class RodaVida extends AuditedBaseOneByUsuarioEntity {

    private static final long serialVersionUID = -1723760582908883735L;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "espiritualidade", precision = 3, scale = 2, nullable = false)
    private BigDecimal espiritualidade = BigDecimal.ZERO;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "entretenimento", precision = 3, scale = 2, nullable = false)
    private BigDecimal entretenimento = BigDecimal.ZERO;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "dinheiro", precision = 3, scale = 2, nullable = false)
    private BigDecimal dinheiro = BigDecimal.ZERO;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "carreira", precision = 3, scale = 2, nullable = false)
    private BigDecimal carreira = BigDecimal.ZERO;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "desenvolvimentoPessoal", precision = 3, scale = 2, nullable = false)
    private BigDecimal desenvolvimentoPessoal = BigDecimal.ZERO;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "relacionamento", precision = 3, scale = 2, nullable = false)
    private BigDecimal relacionamento = BigDecimal.ZERO;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "saude", precision = 3, scale = 2, nullable = false)
    private BigDecimal saude = BigDecimal.ZERO;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "ambiente", precision = 3, scale = 2, nullable = false)
    private BigDecimal ambiente = BigDecimal.ZERO;

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
