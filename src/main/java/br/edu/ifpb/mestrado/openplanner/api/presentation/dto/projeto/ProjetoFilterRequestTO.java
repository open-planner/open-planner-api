package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Prioridade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Projeto;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tag.Tag;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecBetween;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecJoin;

@SpecEntity(Projeto.class)
public class ProjetoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -792829285160148170L;

    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @SpecBetween(left = "periodo.dataInicio", right = "periodo.dataFim")
    private LocalDate data;

    // TODO checar possibilidade
//  @SpecGroup(operator = Operator.OR)
//  private PeriodoFilterRequestTO periodo;

    @SpecField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
    private String descricao;

    private Prioridade prioridade;

    private Status status;

    @SpecJoin(properties = { "tags" }, entity = Tag.class)
    @SpecField(value = "id")
    private Long tag;

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

//  public PeriodoFilterRequestTO getPeriodo() {
//      return periodo;
//  }
//
//  public void setPeriodo(PeriodoFilterRequestTO periodo) {
//      this.periodo = periodo;
//  }

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

    public Long getTag() {
        return tag;
    }

    public void setTag(Long tag) {
        this.tag = tag;
    }

}
