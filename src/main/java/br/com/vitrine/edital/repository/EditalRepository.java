package br.com.vitrine.edital.repository;


import br.com.vitrine.edital.model.entity.Edital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EditalRepository extends JpaRepository<Edital, Long> {

    Optional<Edital> findByNome(String nome);

    List<Edital> findAllByOrderByNomeAsc();

}
