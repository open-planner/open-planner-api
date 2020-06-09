package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.tarefa;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa.Status;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa.Tarefa;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecEntity;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecField;

@SpecEntity(Tarefa.class)
public class TarefaFilterRequestTO implements Serializable {

    private static final long serialVersionUID = 5002772490664267241L;

    private Long id;

    @DateTimeFormat(iso = ISO.DATE)
    @SpecField(value = "dataHora", operation = Operation.DATETIME_TO_DATE)
    private LocalDate data;

    @SpecField(operation = Operation.LIKE_IGNORE_CASE_UNACCENT)
    private String descricao;

    private Status status;

    @SpecField(value = "tags.id")
    private Long tags;

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

}
