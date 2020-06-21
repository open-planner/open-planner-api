package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.meta;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.github.fagnerlima.springspecificationtools.SpecOperation;
import com.github.fagnerlima.springspecificationtools.annotation.SpecEntity;
import com.github.fagnerlima.springspecificationtools.annotation.SpecField;
import com.github.fagnerlima.springspecificationtools.annotation.SpecJoin;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.meta.Meta;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.meta.Status;

@SpecEntity(Meta.class)
public class MetaFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 7793931799975538097L;

    private Long id;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate data;

    @SpecField(operation = SpecOperation.LIKE_IGNORE_CASE_UNACCENT)
    private String descricao;

    private Status status;

    @SpecJoin
    @SpecField("tags.id")
    private Long tagId;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getNotificacaoId() {
        return notificacaoId;
    }

    public void setNotificacaoId(Long notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

}
