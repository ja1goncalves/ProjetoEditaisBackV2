package br.com.vitrine.edital.service;

import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.model.entity.Perfil;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.OrgaoFomentoRepository;
import br.com.vitrine.edital.repository.PerfilRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import br.com.vitrine.edital.utils.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    private final PerfilRepository perfilRepository;
    private final OrgaoFomentoRepository orgaoFomentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordUtils passwordUtils;

    public DBService(PerfilRepository perfilRepository, OrgaoFomentoRepository orgaoFomentoRepository, UsuarioRepository usuarioRepository, PasswordUtils passwordUtils) {
        this.perfilRepository = perfilRepository;
        this.orgaoFomentoRepository = orgaoFomentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordUtils = passwordUtils;
    }

    public void criarPerfis() {
        if (perfilRepository.findAll().isEmpty()) {
            Perfil perfil1 = Perfil.builder().descricao("Admin").build();
            Perfil perfil2 = Perfil.builder().descricao("Empresa").build();
            Perfil perfil3 = Perfil.builder().descricao("Outros").build();

            perfilRepository.saveAll(Arrays.asList(perfil1, perfil2, perfil3));
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
                    .senha(passwordUtils.encriptar("12345678"))
                    .perfil(perfil)
                    .build();

            usuarioRepository.save(usuario);
        }
    }

}
