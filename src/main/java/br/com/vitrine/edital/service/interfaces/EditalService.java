package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface EditalService {

    EditalDTO create(EditalDTO editalDTO);

    void inserirPdf(Long idEdital, MultipartFile pdf);

    EditalDTO recover(Long idEdital);

    byte[] recoverPdf(Long idEdital);

    EditalDTO update(EditalDTO editalDTO);

    void delete(Long idEdital);

    List<EditalDTO> getAll();

    void favoritarEdital(Long idEdital, Long idUsuario);

    void desfavoritarEdital(Long idEdital, Long idUsuario);

    Set<UsuarioDTO> getUsuariosQueFavoritaram(Long idEdital);

    List<EditalDTO> getEditalByUserFilterAndBot(Long idUsuario);
}
