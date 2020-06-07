package br.edu.ifpb.mestrado.openplanner.api.test.builder;

import java.math.BigDecimal;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.rodavida.RodaVida;

public class RodaVidaBuilder {

    private RodaVida rodaVida;

    public RodaVidaBuilder() {
        super();
        rodaVida = new RodaVida();
    }

    public RodaVidaBuilder(RodaVida rodaVida) {
        super();
        this.rodaVida = rodaVida;
    }

    public RodaVida build() {
        return rodaVida;
    }

    public RodaVidaBuilder withEspiritualidade(BigDecimal espiritualidade) {
        rodaVida.setEspiritualidade(espiritualidade);
        return this;
    }

    public RodaVidaBuilder withEntretenimento(BigDecimal entretenimento) {
        rodaVida.setEntretenimento(entretenimento);
        return this;
    }

    public RodaVidaBuilder withDinheiro(BigDecimal dinheiro) {
        rodaVida.setDinheiro(dinheiro);
        return this;
    }

    public RodaVidaBuilder withCarreira(BigDecimal carreira) {
        rodaVida.setCarreira(carreira);
        return this;
    }

    public RodaVidaBuilder withDesenvolvimentoPessoal(BigDecimal desenvolvimentoPessoal) {
        rodaVida.setDesenvolvimentoPessoal(desenvolvimentoPessoal);
        return this;
    }

    public RodaVidaBuilder withRelacionamento(BigDecimal relacionamento) {
        rodaVida.setRelacionamento(relacionamento);
        return this;
    }

    public RodaVidaBuilder withSaude(BigDecimal saude) {
        rodaVida.setSaude(saude);
        return this;
    }

    public RodaVidaBuilder withAmbiente(BigDecimal ambiente) {
        rodaVida.setAmbiente(ambiente);
        return this;
    }

}
