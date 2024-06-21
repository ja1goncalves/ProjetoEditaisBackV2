package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
