package br.ufac.edgeneoapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufac.edgeneoapi.model.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    Optional<Disciplina> findByDisciplinaCodigo(String disciplinaCodigo);
}
