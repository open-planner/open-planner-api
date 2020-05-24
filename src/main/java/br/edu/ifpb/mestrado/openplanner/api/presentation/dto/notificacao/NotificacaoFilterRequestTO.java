package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;

public class NotificacaoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 4848916736116970863L;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @SpecField(operation = Operation.LESS_THAN_OR_EQUAL)
    private LocalDateTime dataHora;

    private Boolean lida;

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

}
