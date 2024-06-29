package br.com.vitrine.edital.model.dto;

import br.com.vitrine.edital.model.entity.OrgaoFomento;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrgaoFomentoDTO implements Serializable {

    private static final long serialVersionUID = 63447302565563609L;

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String nome;

    public OrgaoFomentoDTO(OrgaoFomento orgaoFomento) {
        this.id = orgaoFomento.getId();
        this.nome = orgaoFomento.getNome();
    }

}
