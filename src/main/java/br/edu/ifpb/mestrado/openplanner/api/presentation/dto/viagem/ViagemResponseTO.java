package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.viagem;

import java.io.Serializable;
import java.time.LocalDate;

public class ViagemResponseTO implements Serializable {

    private static final long serialVersionUID = -7367903701277015283L;

    private Long id;

    private String tipo;

    private LocalDate data;

    private String destino;

    private String status;

    private String anotacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

}
