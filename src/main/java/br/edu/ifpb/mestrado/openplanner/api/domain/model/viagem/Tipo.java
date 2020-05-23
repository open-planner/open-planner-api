package br.edu.ifpb.mestrado.openplanner.api.domain.model.viagem;

public enum Tipo {

    PESSOAL("Pessoal"),
    PROFISSIONAL("Profissional");

    private String label;

    private Tipo(String label) {
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
