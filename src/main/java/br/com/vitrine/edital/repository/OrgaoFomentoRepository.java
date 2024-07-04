package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.OrgaoFomento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrgaoFomentoRepository extends JpaRepository<OrgaoFomento, Long> {

    Optional<OrgaoFomento> findByNomeIgnoreCase(String nome);

    List<OrgaoFomento> findAllByOrderByNomeAsc();
}
