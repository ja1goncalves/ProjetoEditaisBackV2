package br.com.vitrine.edital.service;

import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.model.entity.Perfil;
import br.com.vitrine.edital.repository.OrgaoFomentoRepository;
import br.com.vitrine.edital.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    private final PerfilRepository perfilRepository;
    private final OrgaoFomentoRepository orgaoFomentoRepository;

    public DBService(PerfilRepository perfilRepository, OrgaoFomentoRepository orgaoFomentoRepository) {
        this.perfilRepository = perfilRepository;
        this.orgaoFomentoRepository = orgaoFomentoRepository;
    }

    public void criarPerfis() {
        if (perfilRepository.findAll().isEmpty()) {
            Perfil perfil1 = Perfil.builder().descricao("Admin").build();
            Perfil perfil2 = Perfil.builder().descricao("Outros").build();

            perfilRepository.saveAll(Arrays.asList(perfil1, perfil2));
        }
    }

    public void criarOrgaosDeFomento() {
        if (orgaoFomentoRepository.findAll().isEmpty()) {
            OrgaoFomento orgaoFomento1 = OrgaoFomento.builder().nome("FACEPE").build();
            OrgaoFomento orgaoFomento2 = OrgaoFomento.builder().nome("Finep").build();

            orgaoFomentoRepository.saveAll(Arrays.asList(orgaoFomento1, orgaoFomento2));
        }
    }

}
