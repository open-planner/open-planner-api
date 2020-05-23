package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

public enum RecorrenciaTimeUnit {

    DAY("Diária"),
    WEEK("Semanal"),
    MONTH("Mensal"),
    YEAR("Anual");

    private String label;

    private RecorrenciaTimeUnit(String label) {
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
