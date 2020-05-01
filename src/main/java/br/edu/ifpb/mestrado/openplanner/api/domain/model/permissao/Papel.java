package br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao;

public enum Papel {

    ROLE_ROOT,
    ROLE_SYSTEM,
    ROLE_ADMIN;

    public Boolean isRoot() {
        return this.equals(ROLE_ROOT);
    }

    public Boolean isSystem() {
        return this.equals(ROLE_SYSTEM);
    }

    public Boolean isAdmin() {
        return this.equals(ROLE_ADMIN);
    }

}
