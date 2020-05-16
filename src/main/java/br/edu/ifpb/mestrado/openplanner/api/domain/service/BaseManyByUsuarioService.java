package br.edu.ifpb.mestrado.openplanner.api.domain.service;

import java.util.List;

public interface BaseManyByUsuarioService<T> extends BaseService<T> {

    public List<T> findByUsuarioAutenticado();

}
