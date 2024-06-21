package br.com.vitrine.edital.service;

import br.com.vitrine.edital.exception.NaoEncontradoException;
import br.com.vitrine.edital.exception.UsuarioException;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.model.entity.Perfil;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.PerfilRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import br.com.vitrine.edital.service.interfaces.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        if (isNull(usuarioDTO)) {
            throw new UsuarioException("Usuário enviado para cadastro inválido");
        }

        usuarioDTO.setId(null);
        long idCriado = saveOrUpdate(usuarioDTO).getId();
        usuarioDTO.setId(idCriado);

        return usuarioDTO;
    }

    @Override
    public UsuarioDTO recover(Long idUsuario) {
        Usuario usuario = getUsuarioOrThrow(idUsuario);
        return new UsuarioDTO(usuario);
    }

    @Override
    public UsuarioDTO update(UsuarioDTO usuarioDTO) {
        if (isNull(usuarioDTO)) {
            throw new UsuarioException("Usuário enviado para Edição inválido");
        }

        if (isNull(usuarioDTO.getId()) || usuarioDTO.getId() == 0) {
            throw new UsuarioException("Id do usuário está inválido: " + usuarioDTO.getId());
        }

        Usuario usuario = saveOrUpdate(usuarioDTO);
        return new UsuarioDTO(usuario);
    }

    @Override
    public void delete(Long idUsuario) {
        getUsuarioOrThrow(idUsuario);
        usuarioRepository.deleteById(idUsuario);
    }

    @Override
    public List<UsuarioDTO> getAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    private Usuario saveOrUpdate(UsuarioDTO usuarioDTO) {
        Perfil perfil = getPerfilOrThrow(usuarioDTO.getIdPerfil());
        Usuario usuario = new Usuario(usuarioDTO, perfil);
        return usuarioRepository.save(usuario);
    }

    private Perfil getPerfilOrThrow(long idPerfil) {
        return perfilRepository
                .findById(idPerfil)
                .orElseThrow(() -> new NaoEncontradoException("Perfil não encontrado: " + idPerfil));
    }

    private Usuario getUsuarioOrThrow(long idUsuario) {
        return usuarioRepository
                .findById(idUsuario)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado: " + idUsuario));
    }
}
