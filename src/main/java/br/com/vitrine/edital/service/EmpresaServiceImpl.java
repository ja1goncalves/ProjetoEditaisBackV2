package br.com.vitrine.edital.service;


import br.com.vitrine.edital.exception.DadoInvalidoException;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.service.interfaces.EmpresaService;
import br.com.vitrine.edital.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.com.vitrine.edital.model.dto.EmpresaDTO;
import br.com.vitrine.edital.model.entity.Empresa;
import br.com.vitrine.edital.repository.EmpresaRepository;

import static java.util.Objects.isNull;

@Service
public class EmpresaServiceImpl implements EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final UsuarioService usuarioService;
    
    public EmpresaServiceImpl(EmpresaRepository empresaRepository, UsuarioService usuarioService) {
        this.empresaRepository = empresaRepository;
        this.usuarioService = usuarioService;
    }

    // Método para criar ou atualizar uma empresa
    public EmpresaDTO saveEmpresa(EmpresaDTO empresaDTO) {
        if (isNull(empresaDTO)) {
            throw new DadoInvalidoException("Usuário enviado para cadastro inválido");
        }

        Usuario usuario = this.usuarioService.create(empresaDTO.getUsuario());
        Empresa empresa = empresaRepository.save(new Empresa(empresaDTO, usuario));  // Método do JpaRepository para salvar ou atualizar

        return new EmpresaDTO(empresa);
    }

    // Método para buscar uma empresa pelo ID
    public  Optional<Empresa> getEmpresaById(Long id) {
        return empresaRepository.findById(id);
    }

    // Método para buscar todas as empresas
    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    // Método para deletar uma empresa pelo ID
    public void deleteEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
    
    public void findNomeFantasia(String string){
        empresaRepository.findByNomeFantasia(string);
    }
    public void findCnpj(String string){
        empresaRepository.findByCnpj(string);
    }

}