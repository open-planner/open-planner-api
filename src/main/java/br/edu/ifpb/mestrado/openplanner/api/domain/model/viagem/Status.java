package br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem;

public enum Status {

    PLANEJADA("Planejada"),
    CONCLUIDA("Concluída"),
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
