package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.rodavida;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RodaVidaRequesTO implements Serializable {

    private static final long serialVersionUID = -2183449646109908518L;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal espiritualidade;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal entretenimento;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal dinheiro;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal carreira;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal desenvolvimentoPessoal;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal relacionamento;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal saude;

    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(1)
    private BigDecimal ambiente;

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

    @Override
    public String toString() {
        return String.format(
                "RodaVidaRequesTO [espiritualidade=%s, entretenimento=%s, dinheiro=%s, carreira=%s, desenvolvimentoPessoal=%s, relacionamento=%s, saude=%s, ambiente=%s]",
                espiritualidade, entretenimento, dinheiro, carreira, desenvolvimentoPessoal, relacionamento, saude, ambiente);
    }

}
