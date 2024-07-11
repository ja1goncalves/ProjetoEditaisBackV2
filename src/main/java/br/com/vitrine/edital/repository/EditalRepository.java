package br.com.vitrine.edital.repository;


import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EditalRepository extends JpaRepository<Edital, Long> {

    Optional<Edital> findByNome(String nome);

    List<Edital> findAllByOrderByNomeAsc();

    @Query("SELECT e.usuariosQueFavoritaram FROM Edital e WHERE e.id = :idEdital")
    Set<Usuario> findUsuariosQueFavoritaramByIdEdital(@Param("idEdital") Long idEdital);

    @Query("SELECT e FROM Edital e WHERE e.usuario.id IN :ids ORDER BY LOWER(e.nome) ASC")
    List<Edital> findByUsers(@Param("ids") List<Long> ids);

}
