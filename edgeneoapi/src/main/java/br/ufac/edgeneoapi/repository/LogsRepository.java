package br.ufac.edgeneoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufac.edgeneoapi.model.Logs;

public interface LogsRepository extends JpaRepository<Logs, Long> {

}
