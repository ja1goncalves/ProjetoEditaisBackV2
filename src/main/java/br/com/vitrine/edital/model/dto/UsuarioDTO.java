package br.com.vitrine.edital.model.dto;

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
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 2391473986842882960L;

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String login;

    private String senha;

    private String nome;

    private Long idPerfil;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.nome = usuario.getNome();
        this.idPerfil = usuario.getPerfil().getId();
    }


}
