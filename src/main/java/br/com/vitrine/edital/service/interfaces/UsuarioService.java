package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.CredencialDTO;
import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.model.entity.Usuario;

import java.util.List;
import java.util.Set;

public interface UsuarioService {

    Usuario create(UsuarioDTO usuarioDTO);

    UsuarioDTO recoverById(Long idUsuario);

    UsuarioDTO recoverByLogin(String login);

    UsuarioDTO update(UsuarioDTO usuarioDTO);

    void delete(Long idUsuario);

    List<UsuarioDTO> getAll();

    UsuarioDTO login(CredencialDTO credencialDTO);

    Set<EditalDTO> getEditaisFavoritos(Long idUsuario);
}
