package br.com.vitrine.edital.model.dto;

import br.com.vitrine.edital.model.entity.Perfil;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfilDTO implements Serializable {

    private static final long serialVersionUID = -836520936464130360L;

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String descricao;

    public PerfilDTO(Perfil perfil) {
        this.id = perfil.getId();
        this.descricao = perfil.getDescricao();
    }

}
