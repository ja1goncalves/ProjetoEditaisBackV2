package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    List<Usuario> findAllByOrderByNomeAsc();

}
