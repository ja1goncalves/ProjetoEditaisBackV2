package br.com.vitrine.edital.model.dto;

import br.com.vitrine.edital.model.entity.Empresa;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class EmpresaDTO {
	private static final long serialVersionUID = -836520936464130360L;

	@EqualsAndHashCode.Include
	private Long id;
	private String nomeFantasia;
	private String cnpj;
	private UsuarioDTO usuario;
	
	public EmpresaDTO(Empresa empresa) {
		this.id = empresa.getId();
		this.nomeFantasia = empresa.getNomeFantasia();
		this.cnpj = empresa.getCnpj();
		this.usuario = new UsuarioDTO(empresa.getUsuario());
	}
}
