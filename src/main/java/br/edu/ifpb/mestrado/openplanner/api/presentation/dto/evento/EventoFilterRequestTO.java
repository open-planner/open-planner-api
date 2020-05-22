package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class EventoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -530735338790696989L;

    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInicio;

    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
