
package br.com.vitrine.edital.model.entity;


import br.com.vitrine.edital.model.dto.EmpresaDTO;
import br.com.vitrine.edital.model.dto.PerfilDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Empresa{
	@Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
	
	@Column(name="CNPJ", nullable=false)
	private String cnpj;
	
	@Column(name="NOMEFANTASIA", nullable=false)
	private String nomeFantasia;
	@OneToOne
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    private Usuario usuario;
	
	public Empresa(EmpresaDTO empresaDTO) {
        this.id = empresaDTO.getId();
        this.cnpj = empresaDTO.getCnpj();
        this.nomeFantasia = empresaDTO.getNomeFantasia();
    }
}