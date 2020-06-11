package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.evento;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecJoin;

@SpecEntity(Evento.class)
public class EventoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -530735338790696989L;

    private Long id;

    @DateTimeFormat(iso = ISO.DATE)
    @SpecField(value = "dataHora", operation = Operation.DATETIME_TO_DATE)
    private LocalDate data;

    @SpecField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
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
