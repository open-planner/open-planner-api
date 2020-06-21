package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.notificacao;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.github.fagnerlima.springspecificationtools.SpecOperation;
import com.github.fagnerlima.springspecificationtools.annotation.SpecEntity;
import com.github.fagnerlima.springspecificationtools.annotation.SpecField;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.TipoNotificacao;

@SpecEntity(Notificacao.class)
public class NotificacaoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 4848916736116970863L;

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @SpecField(operation = SpecOperation.LESS_THAN_OR_EQUAL)
    private LocalDateTime dataHora;

    private TipoNotificacao tipo;

    private Boolean lida;

    private Boolean email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacao tipo) {
        this.tipo = tipo;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

}
