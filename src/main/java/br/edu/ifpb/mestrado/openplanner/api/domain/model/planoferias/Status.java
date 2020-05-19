package br.edu.ifpb.mestrado.openplanner.api.domain.model.planoferias;

public enum Status {

    PLANEJADO("Planejado"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private String label;

    private Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
