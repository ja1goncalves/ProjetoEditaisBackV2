package br.com.vitrine.edital.model.entity;

import br.com.vitrine.edital.model.dto.PreProjetoDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PRE_PROJETO")
public class PreProjeto {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Lob
    @Column(name = "PDF")
    private byte[] pdf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false, referencedColumnName = "ID")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EDITAL", nullable = false, referencedColumnName = "ID")
    private Edital edital;

    public PreProjeto(PreProjetoDTO preProjetoDTO, Usuario usuario, Edital edital) {
        this.id = preProjetoDTO.getId();
        this.usuario = usuario;
        this.edital = edital;
    }

    public PreProjeto(PreProjetoDTO preProjetoDTO, Usuario usuario, Edital edital, byte[] pdf) {
        this(preProjetoDTO, usuario, edital);
        this.pdf = pdf;
    }


}
