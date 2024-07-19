package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.PreProjetoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PreProjetoService {
    PreProjetoDTO create(PreProjetoDTO preProjetoDTO);
    List<PreProjetoDTO> getAll();
    PreProjetoDTO recover(Long id_preProjeto);
   // List<PreProjetoDTO> getAllByEdital(Long id_edital);
   // List<PreProjetoDTO> findByIdUsuario(Long id_usuario);
    PreProjetoDTO update(PreProjetoDTO preProjetoDTO);
    void delete(Long id);
    void inserirPdfPreProjeto(Long idPreProjeto, MultipartFile pdf);
}

