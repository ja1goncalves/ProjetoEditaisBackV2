package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
