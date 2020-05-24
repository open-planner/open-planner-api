package br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao;

public enum TipoNotificacao {

    EVENTO("Evento"),
    PROJETO("Projeto"),
    META("Meta"),
    TAREFA("Tarefa");

    private String label;

    private TipoNotificacao(String label) {
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
