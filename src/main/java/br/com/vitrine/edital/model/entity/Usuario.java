package br.com.vitrine.edital.model.entity;

import br.com.vitrine.edital.model.dto.UsuarioDTO;
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
public class Usuario {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL", nullable = false, referencedColumnName = "ID")
    private Perfil perfil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PreProjeto> preProjetos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Edital> editais;

    public Usuario(UsuarioDTO usuarioDTO, Perfil perfil) {
        this.id = usuarioDTO.getId();
        this.login = usuarioDTO.getLogin();
        this.senha = usuarioDTO.getSenha();
        this.nome = usuarioDTO.getNome();
        this.perfil = perfil;
    }


}
