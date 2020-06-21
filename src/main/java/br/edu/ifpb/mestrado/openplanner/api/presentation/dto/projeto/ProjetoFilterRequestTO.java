package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.github.fagnerlima.springspecificationtools.SpecOperation;
import com.github.fagnerlima.springspecificationtools.annotation.SpecBetween;
import com.github.fagnerlima.springspecificationtools.annotation.SpecEntity;
import com.github.fagnerlima.springspecificationtools.annotation.SpecField;
import com.github.fagnerlima.springspecificationtools.annotation.SpecJoin;
import com.github.fagnerlima.springspecificationtools.annotation.SpecPeriod;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Prioridade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Projeto;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Status;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoFilterRequestTO;

@SpecEntity(Projeto.class)
public class ProjetoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -792829285160148170L;

    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @SpecBetween(left = "periodo.dataInicio", right = "periodo.dataFim")
    private LocalDate data;

    @SpecPeriod(start = "periodo.dataInicio", end = "periodo.dataFim")
    private PeriodoFilterRequestTO periodo;

    @SpecField(operation = SpecOperation.LIKE_IGNORE_CASE_UNACCENT)
    private String descricao;

    private Prioridade prioridade;

    private Status status;

    @SpecJoin
    @SpecField(value = "tags.id")
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

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
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
