package br.ufac.edgeneoapi.service;

import java.util.List;

public interface IService<T> {
    List<T> getAll();
    T getById(Long id);
    // List<T> busca(String termoBusca);
    T save(T objeto);
    void delete(Long id);
}