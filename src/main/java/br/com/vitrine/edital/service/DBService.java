package br.com.vitrine.edital.service;

import br.com.vitrine.edital.model.entity.Perfil;
import br.com.vitrine.edital.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    private final PerfilRepository perfilRepository;

    public DBService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public void criarPerfis() {
        if (perfilRepository.findAll().isEmpty()) {
            Perfil perfil1 = Perfil.builder().descricao("Admin").build();
            Perfil perfil2 = Perfil.builder().descricao("Outros").build();

            perfilRepository.saveAll(Arrays.asList(perfil1, perfil2));
        }
    }

}
