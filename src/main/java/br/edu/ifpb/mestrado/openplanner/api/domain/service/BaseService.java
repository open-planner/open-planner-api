package br.edu.ifpb.mestrado.openplanner.api.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseService<T> {

    public T findById(Long id);

    public List<T> findAll();

    public Page<T> findAll(Pageable pageable);

    public Page<T> findAll(Specification<T> specification, Pageable pageable);

    public T save(T entity);

    public T update(Long id, T entity);

    public void deleteById(Long id);

}
