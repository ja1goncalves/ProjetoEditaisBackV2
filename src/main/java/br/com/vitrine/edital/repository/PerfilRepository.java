package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByDescricaoIgnoreCase(String descricao);

}
