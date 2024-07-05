package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.CredencialDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO create(UsuarioDTO usuarioDTO);

    UsuarioDTO recoverById(Long idUsuario);

    UsuarioDTO recoverByLogin(String login);

    UsuarioDTO update(UsuarioDTO usuarioDTO);

    void delete(Long idUsuario);

    List<UsuarioDTO> getAll();

    UsuarioDTO login(CredencialDTO credencialDTO);
}
