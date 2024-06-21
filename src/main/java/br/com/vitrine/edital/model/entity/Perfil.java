package br.com.vitrine.edital.model.entity;

import br.com.vitrine.edital.model.dto.PerfilDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PERFIL")
public class Perfil {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Usuario> usuarios;

    public Perfil(PerfilDTO perfilDTO) {
        this.id = perfilDTO.getId();
        this.descricao = perfilDTO.getDescricao();
    }

}
