package br.ufac.edgeneoapi.repository;

import br.ufac.edgeneoapi.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    Optional<Usuario> findByConfirmacaoEmailToken(String token);
    // @Query("SELECT u FROM Usuario u WHERE u.nome LIKE %?1% OR u.email LIKE %?1%")
    // List<Usuario> busca(String termoBusca);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

}
