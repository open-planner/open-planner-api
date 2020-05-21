package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoResponseTO;

public class EventoReducedResponseTO implements Serializable {

    private static final long serialVersionUID = -2890983806375630404L;

    private Long id;

    private PeriodoResponseTO periodo;

    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PeriodoResponseTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoResponseTO periodo) {
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
