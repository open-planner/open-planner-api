package br.edu.ifpb.mestrado.openplanner.api.domain.service;

public interface BaseOneByUsuarioService<T> extends BaseService<T> {

    public T findByUsuarioAutenticado();

    public T updateByUsuarioAutenticado(T entity);

}
