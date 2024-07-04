package br.com.vitrine.edital.model.entity;


import br.com.vitrine.edital.model.dto.OrgaoFomentoDTO;
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
@Table(name = "ORGAO_FOMENTO")
public class OrgaoFomento {


    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "orgaoFomento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Edital> editais;


    public OrgaoFomento(OrgaoFomentoDTO orgaoFomentoDTO) {
        this.id = orgaoFomentoDTO.getId();
        this.nome = orgaoFomentoDTO.getNome();
    }


}
