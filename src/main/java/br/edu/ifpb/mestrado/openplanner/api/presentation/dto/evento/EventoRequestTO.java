package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoRequestTO;

public class EventoRequestTO implements Serializable {

    private static final long serialVersionUID = 5177952186914054795L;

    @Valid
    private PeriodoRequestTO periodo;

    @NotBlank
    @Size(min = 3, max = 64)
    private String descricao;

    public PeriodoRequestTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoRequestTO periodo) {
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
