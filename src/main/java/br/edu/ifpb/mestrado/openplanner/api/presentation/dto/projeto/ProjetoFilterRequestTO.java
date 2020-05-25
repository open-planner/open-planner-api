package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.projeto;

import java.io.Serializable;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Prioridade;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto.Status;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.PeriodoFilterRequestTO;

public class ProjetoFilterRequestTO implements Serializable {

    private static final long serialVersionUID = -792829285160148170L;

    private Long id;

    private PeriodoFilterRequestTO periodo;

    @SpecField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
    private String descricao;

    private Prioridade prioridade;

    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
