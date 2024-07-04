package br.com.vitrine.edital.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Edital {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String nome;

    private String categoria;

    @Column(name = "PUBLICO_ALVO")
    private String publicoAlvo;

    private String area;

    @Column(name = "DATA_PUBLICACAO")
    private LocalDateTime dataPublicacao;

    @Column(name = "DATA_INICIAL")
    private LocalDateTime dataInicial;

    @Column(name = "DATA_FINAL")
    private LocalDateTime dataFinal;

    private String resultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGAO_FOMENTO", nullable = false, referencedColumnName = "ID")
    private OrgaoFomento orgaoFomento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false, referencedColumnName = "ID")
    private Usuario usuario;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PreProjeto> preProjetos;

    @Lob
    @Column(name = "PDF")
    private byte[] pdf;


}
