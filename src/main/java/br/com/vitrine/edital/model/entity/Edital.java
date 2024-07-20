package br.com.vitrine.edital.model.entity;

import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.utils.DataUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.Objects.nonNull;

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

    @Lob
    @Column(name = "PDF")
    private byte[] pdf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGAO_FOMENTO", nullable = false, referencedColumnName = "ID")
    private OrgaoFomento orgaoFomento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false, referencedColumnName = "ID")
    private Usuario usuario;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PreProjeto> preProjetos;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "EDITAL_FAVORITO",
            joinColumns = @JoinColumn(name = "ID_EDITAL", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    )
    private Set<Usuario> usuariosQueFavoritaram;

    public Edital(EditalDTO editalDTO, Usuario usuario, OrgaoFomento orgaoFomento) {
        this.id = editalDTO.getId();
        this.nome = editalDTO.getNome();
        this.categoria = editalDTO.getCategoria();
        this.publicoAlvo = editalDTO.getPublicoAlvo();
        this.area = editalDTO.getArea();
        this.dataPublicacao = isValidString(editalDTO.getDataPublicacao()) ? DataUtils.obterLocalDateTime(editalDTO.getDataPublicacao()) : null;
        this.dataInicial = isValidString(editalDTO.getDataInicial()) ? DataUtils.obterLocalDateTime(editalDTO.getDataInicial()) : null;
        this.dataFinal = isValidString(editalDTO.getDataFinal()) ? DataUtils.obterLocalDateTime(editalDTO.getDataFinal()) : null;
        this.resultado = editalDTO.getResultado();
        this.usuario = usuario;
        this.orgaoFomento = orgaoFomento;
    }

    public Edital(EditalDTO editalDTO, Usuario usuario, OrgaoFomento orgaoFomento, byte[] pdf) {
        this(editalDTO, usuario, orgaoFomento);
        this.pdf = pdf;
    }

    private boolean isValidString(String s) {
        return nonNull(s) && !s.isBlank();
    }


}
