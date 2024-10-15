package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.Empresa;
import br.com.vitrine.edital.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByCnpj(String cnpj);

    List<Empresa> findByNomeFantasia(String nome);

    List<Empresa> findByUsuario(Usuario usuario);

}

