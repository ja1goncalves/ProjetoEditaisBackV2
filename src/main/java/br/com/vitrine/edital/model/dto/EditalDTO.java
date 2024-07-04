package br.com.vitrine.edital.model.dto;

import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.utils.DataUtils;
import lombok.*;

import java.io.Serializable;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EditalDTO implements Serializable {

    private static final long serialVersionUID = 1813766172183159764L;

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String nome;

    private String categoria;

    private String publicoAlvo;

    private String area;

    private String dataPublicacao;

    private String dataInicial;

    private String dataFinal;

    private String resultado;

    private long idUsuario;

    private long idOrgaoFomento;

    private boolean isCriadoPorBot;

    public EditalDTO(Edital edital, Usuario usuario, OrgaoFomento orgaoFomento) {
        this.id = edital.getId();
        this.nome = edital.getNome();
        this.categoria = edital.getCategoria();
        this.publicoAlvo = edital.getPublicoAlvo();
        this.area = edital.getArea();
        this.dataPublicacao = nonNull(edital.getDataPublicacao()) ? DataUtils.formatarDataHora(edital.getDataPublicacao()) : "";
        this.dataInicial = nonNull(edital.getDataInicial()) ? DataUtils.formatarDataHora(edital.getDataInicial()) : "";
        this.dataFinal = nonNull(edital.getDataFinal()) ? DataUtils.formatarDataHora(edital.getDataFinal()) : "";
        this.resultado = edital.getResultado();
        this.idUsuario = usuario.getId();
        this.idOrgaoFomento = orgaoFomento.getId();
        this.isCriadoPorBot = usuario.getLogin().equalsIgnoreCase("BOT");
    }
}
