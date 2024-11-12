package br.ufac.edgeneoapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IController<T> {
    
    public ResponseEntity<List<T>> get(String termoBusca);
    public ResponseEntity<T> get(Long id);
    public ResponseEntity<?> insert(T objeto);
    public ResponseEntity<T> update(T objeto);
    public ResponseEntity<?> delete(Long id);
    
}
