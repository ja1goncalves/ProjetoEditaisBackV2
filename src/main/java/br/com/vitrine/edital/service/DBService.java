package br.com.vitrine.edital.service;

import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.model.entity.Perfil;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.OrgaoFomentoRepository;
import br.com.vitrine.edital.repository.PerfilRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    private final PerfilRepository perfilRepository;
    private final OrgaoFomentoRepository orgaoFomentoRepository;
    private final UsuarioRepository usuarioRepository;

    public DBService(PerfilRepository perfilRepository, OrgaoFomentoRepository orgaoFomentoRepository, UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.orgaoFomentoRepository = orgaoFomentoRepository;
        this.usuarioRepository = usuarioRepository;
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

    public void criarUsuarioBot() {
        if (!usuarioRepository.findByLoginIgnoreCase("Bot").isPresent()) {
            Perfil perfil = perfilRepository.findByDescricaoIgnoreCase("Admin").get();
            Usuario usuario = Usuario.builder()
                    .nome("Bot")
                    .login("bot")
                    .senha("")
                    .perfil(perfil)
                    .build();

            usuarioRepository.save(usuario);
        }
    }

}
