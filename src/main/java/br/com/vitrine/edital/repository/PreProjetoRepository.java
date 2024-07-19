package br.com.vitrine.edital.repository;

import br.com.vitrine.edital.model.entity.PreProjeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreProjetoRepository extends JpaRepository<PreProjeto, Long> {

    ///Optional<PreProjeto> findByIdUsuario(usuario.get);

   // Optional<PreProjeto> findByIdEdital(Long idEdital);

    /*
    @Query("SELECT e FROM PreProjeto e WHERE e.usuario.id IN :ids ORDER BY LOWER(e.nome) ASC")
    List<PreProjeto> findByUsers(@Param("ids") List<Long> ids);
    */
}

//List<PreProjetoDTO> getAllByEdital(Long id_edital);
//List<PreProjetoDTO> findByIdUsuario(Long id_usuario);