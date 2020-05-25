package br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto;

public enum Prioridade {

    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
    URGENTE("Urgente");

    private String label;

    private Prioridade(String label) {
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
