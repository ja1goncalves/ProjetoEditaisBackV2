package br.com.vitrine.edital.repository;


import br.com.vitrine.edital.model.entity.Edital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditalRepository extends JpaRepository<Edital, Long> {
}
