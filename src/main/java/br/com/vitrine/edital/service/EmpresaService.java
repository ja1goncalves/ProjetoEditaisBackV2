package br.com.vitrine.edital.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.com.vitrine.edital.model.dto.EmpresaDTO;
import br.com.vitrine.edital.model.entity.Empresa;
import br.com.vitrine.edital.repository.EmpresaRepository;
@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository ;
    
    public EmpresaService(EmpresaRepository empresaRepository){
        this.empresaRepository = empresaRepository;
    }
    
    

    // Método para criar ou atualizar uma empresa
    

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

  
    public Empresa saveEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);  // Método do JpaRepository para salvar ou atualizar
    }    
    

}