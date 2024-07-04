package br.com.vitrine.edital.service;

import br.com.vitrine.edital.exception.DadoInvalidoException;
import br.com.vitrine.edital.exception.NaoEncontradoException;
import br.com.vitrine.edital.exception.RegistroExistenteException;
import br.com.vitrine.edital.exception.UsuarioException;
import br.com.vitrine.edital.model.dto.CredencialDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.model.entity.Perfil;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.PerfilRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import br.com.vitrine.edital.service.interfaces.UsuarioService;
import br.com.vitrine.edital.utils.PasswordUtils;
import br.com.vitrine.edital.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordUtils passwordUtils;
    private final Utils utils;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, PasswordUtils passwordUtils, Utils utils) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordUtils = passwordUtils;
        this.utils = utils;
    }

    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        if (isNull(usuarioDTO)) {
            throw new DadoInvalidoException("Usuário enviado para cadastro inválido");
        }

        if (!utils.isValidString(usuarioDTO.getLogin())) {
            throw new DadoInvalidoException("Login do usuário está  inválido");
        }

        validateLogin(usuarioDTO.getLogin());

        usuarioDTO.setId(null);
        usuarioDTO.setSenha(passwordUtils.encriptar(usuarioDTO.getSenha()));
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

        Usuario usuario = getUsuarioOrThrow(usuarioDTO.getId());
        if (utils.isValidString(usuarioDTO.getSenha())) {
            usuarioDTO.setSenha(passwordUtils.encriptar(usuarioDTO.getSenha()));
        } else {
            usuarioDTO.setSenha(usuario.getSenha());
        }

        if (utils.isValidString(usuarioDTO.getLogin()) && !usuarioDTO.getLogin().equals(usuario.getLogin())) {
            validateLogin(usuarioDTO.getLogin());
        } else {
            usuarioDTO.setLogin(usuario.getLogin());
        }

        usuario = saveOrUpdate(usuarioDTO);
        return new UsuarioDTO(usuario);
    }

    @Override
    public void delete(Long idUsuario) {
        getUsuarioOrThrow(idUsuario);
        usuarioRepository.deleteById(idUsuario);
    }

    @Override
    public List<UsuarioDTO> getAll() {
        return usuarioRepository.findAllByOrderByNomeAsc()
                .stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO login(CredencialDTO credencialDTO) {
        if (!utils.isValidString(credencialDTO.getLogin()) || !utils.isValidString(credencialDTO.getSenha())) {
            throw new DadoInvalidoException("Login e/ou senha inválido(s)");
        }

        Usuario usuario = usuarioRepository
                .findByLogin(credencialDTO.getLogin())
                .orElseThrow(() -> new NaoEncontradoException(String.format("Login '%s' não encontrado", credencialDTO.getLogin())));

        if (!passwordUtils.isHashValido(credencialDTO.getSenha(), usuario.getSenha())) {
            throw new DadoInvalidoException("Senha inválida");
        }

        return new UsuarioDTO(usuario);
    }

    private Usuario saveOrUpdate(UsuarioDTO usuarioDTO) {
        Perfil perfil = getPerfilOrThrow(usuarioDTO.getIdPerfil());
        return usuarioRepository.save(new Usuario(usuarioDTO, perfil));
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

    private void validateLogin(String login) {
        usuarioRepository
                .findByLogin(login)
                .ifPresent(__ -> {
                    throw new RegistroExistenteException(String.format("Login '%s' já cadastrado para outro usuário.", login));
                });
    }


}
