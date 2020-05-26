package br.edu.ifpb.mestrado.openplanner.api.domain.model.tarefa;

public enum Status {

    PLANEJADA("Planejada"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDA("Concluída"),
    NAO_CONCLUIDA("Não concluída"),
    CANCELADA("Cancelada");

    private String label;

    private Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
