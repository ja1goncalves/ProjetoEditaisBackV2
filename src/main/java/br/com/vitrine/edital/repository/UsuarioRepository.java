package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLoginIgnoreCase(String login);

    List<Usuario> findAllByOrderByNomeAsc();

    @Query("SELECT u.editaisFavoritos FROM Usuario u WHERE u.id = :userId")
    Set<Edital> findEditaisFavoritosByUserId(@Param("userId") Long userId);

}
