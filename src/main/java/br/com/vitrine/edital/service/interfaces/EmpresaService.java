package br.com.vitrine.edital.service.interfaces;


import br.com.vitrine.edital.model.dto.EmpresaDTO;
import br.com.vitrine.edital.model.entity.Empresa;
import br.com.vitrine.edital.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {
    // Método para criar ou atualizar uma empresa
    EmpresaDTO saveEmpresa(EmpresaDTO empresa);

    // Método para buscar uma empresa pelo ID
    Optional<Empresa> getEmpresaById(Long id);

    // Método para buscar todas as empresas
    List<Empresa> getAllEmpresas();

    // Método para deletar uma empresa pelo ID
    void deleteEmpresa(Long id);
    
    void findNomeFantasia(String string);

    void findCnpj(String string);
}