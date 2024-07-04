package br.com.vitrine.edital.model.dto;

import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.PreProjeto;
import br.com.vitrine.edital.model.entity.Usuario;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PreProjetoDTO implements Serializable {

    private static final long serialVersionUID = 4686729065781880274L;

    @EqualsAndHashCode.Include
    private Long id;

    private Long idUsuario;
    private Long idEdital;

    public PreProjetoDTO(PreProjeto preProjeto, Edital edital, Usuario usuario) {
        this.id = preProjeto.getId();
        this.idEdital = edital.getId();
        this.idUsuario = usuario.getId();
    }

}
