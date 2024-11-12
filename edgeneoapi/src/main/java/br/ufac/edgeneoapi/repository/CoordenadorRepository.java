package br.ufac.edgeneoapi.repository;

import br.ufac.edgeneoapi.model.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {

    // @Query("SELECT c FROM Coordenador c" +
    //        " LEFT JOIN Usuario u ON u = c.usuario" +
    //        " LEFT JOIN Curso cu ON cu = c.curso" +
    //        " WHERE u.nome LIKE %?1%" +
    //        " OR u.email LIKE %?1%" +
    //        " OR cu.nome LIKE %?1%" +
    //        " OR c.portaria LIKE %?1%")
    // List<Coordenador> busca(String termoBusca);
}
