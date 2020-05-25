package br.edu.ifpb.mestrado.openplanner.api.domain.model.meta;

public enum Status {

    EM_ANDAMENTO("Em andamento"),
    ATINGIDA("Atingida"),
    NAO_ATINGIDA("Não atingida"),
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
