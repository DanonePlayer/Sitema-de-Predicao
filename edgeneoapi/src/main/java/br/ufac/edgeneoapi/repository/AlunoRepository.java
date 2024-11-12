package br.ufac.edgeneoapi.repository;

import br.ufac.edgeneoapi.model.Aluno;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByUsuarioId(Long usuarioId);
    // @Query("SELECT a FROM Aluno a" +
    //        " LEFT JOIN Usuario u ON u = a.usuario" +
    //        " LEFT JOIN Curso c ON c = a.curso" +
    //        " WHERE u.nome LIKE %?1%" +
    //        " OR u.email LIKE %?1%" +
    //        " OR c.nome LIKE %?1%")
    // List<Aluno> busca(String termoBusca);
}
