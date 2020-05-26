package br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto;

public enum Status {

    PLANEJADO("Planejado"),
    EM_ANDAMENTO("Em andamento"),
    CANCELADO("Cancelado"),
    CONCLUIDO("Concluído"),
    NAO_CONCLUIDO("Não concluído");

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
