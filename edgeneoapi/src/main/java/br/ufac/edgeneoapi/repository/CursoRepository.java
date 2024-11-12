package br.ufac.edgeneoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufac.edgeneoapi.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

   Optional<Curso> findByCodCurso(String codCurso);
}
