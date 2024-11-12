package br.ufac.edgeneoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufac.edgeneoapi.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    
}