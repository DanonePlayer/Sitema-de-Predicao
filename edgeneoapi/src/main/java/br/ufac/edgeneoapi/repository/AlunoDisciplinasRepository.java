package br.ufac.edgeneoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufac.edgeneoapi.model.AlunoDisciplinas;

@Repository
public interface AlunoDisciplinasRepository extends JpaRepository<AlunoDisciplinas, Long> {
}
