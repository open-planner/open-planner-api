package br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao;

public enum Papel {

    ROOT,
    SYSTEM,
    ADMIN;

    public Boolean isRoot() {
        return this.equals(ROOT);
    }

    public Boolean isSystem() {
        return this.equals(SYSTEM);
    }

    public Boolean isAdmin() {
        return this.equals(ADMIN);
    }

}
