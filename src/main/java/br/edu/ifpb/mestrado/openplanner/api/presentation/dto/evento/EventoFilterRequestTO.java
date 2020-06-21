package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.github.fagnerlima.springspecificationtools.SpecOperation;
import com.github.fagnerlima.springspecificationtools.annotation.SpecEntity;
import com.github.fagnerlima.springspecificationtools.annotation.SpecField;
import com.github.fagnerlima.springspecificationtools.annotation.SpecJoin;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;

@SpecEntity(Evento.class)
public class EventoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -530735338790696989L;

    private Long id;

    @DateTimeFormat(iso = ISO.DATE)
    @SpecField(value = "dataHora", operation = SpecOperation.DATETIME_TO_DATE)
    private LocalDate data;

    @SpecField(operation = SpecOperation.LIKE_IGNORE_CASE_UNACCENT)
    private String descricao;

    @SpecJoin
    @SpecField("notificacoes.id")
    private Long notificacaoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getNotificacaoId() {
        return notificacaoId;
    }

    public void setNotificacaoId(Long notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

}
