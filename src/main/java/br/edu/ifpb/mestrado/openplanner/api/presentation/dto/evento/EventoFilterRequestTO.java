package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoFilterRequestTO;

public class EventoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -530735338790696989L;

    private Long id;

    private PeriodoFilterRequestTO periodo;

    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PeriodoFilterRequestTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoFilterRequestTO periodo) {
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
