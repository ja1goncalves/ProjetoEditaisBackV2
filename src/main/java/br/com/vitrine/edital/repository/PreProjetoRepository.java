package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.PreProjeto;
import br.com.vitrine.edital.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreProjetoRepository extends JpaRepository<PreProjeto, Long> {

    Optional<PreProjeto> findByEditalAndUsuario(Edital edital, Usuario usuario);

    List<PreProjeto> findByEdital(Edital edital);

    List<PreProjeto> findByUsuario(Usuario usuario);

}

