package br.edu.ifpb.mestrado.openplanner.api.domain.model.projeto;

public enum Status {

    A_INICIAR("A iniciar"),
    CANCELADO("Cancelado"),
    EM_DESENVOLVIMENTO("Em desenvolvimento"),
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
