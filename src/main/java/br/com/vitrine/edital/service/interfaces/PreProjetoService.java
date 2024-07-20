package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.PreProjetoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PreProjetoService {

    PreProjetoDTO create(PreProjetoDTO preProjetoDTO);

    List<PreProjetoDTO> getAll();

    PreProjetoDTO recover(Long id_preProjeto);

    PreProjetoDTO update(PreProjetoDTO preProjetoDTO);

    void delete(Long id);

    void inserirPdfPreProjeto(Long idPreProjeto, MultipartFile pdf);

    byte[] recoverPdf(Long idPreprojeto);

    byte[] recoverPdfByUsuarioAndEdital(Long idUsuario, Long idEdital);

    PreProjetoDTO inserirPdfPreProjetoByUsuarioAndEdital(Long idUsuario, Long idEdital, MultipartFile pdf);

    List<PreProjetoDTO> obterPreprojetosPorUsuario(Long idUsuario);

    List<PreProjetoDTO> obterPreprojetosPorEdital(Long idEdital);

}

