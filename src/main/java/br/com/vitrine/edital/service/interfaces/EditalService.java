package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.EditalDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EditalService {

    EditalDTO create(EditalDTO editalDTO);

    void inserirPdf(Long idEdital, MultipartFile pdf);

    EditalDTO recover(Long idEdital);

    byte[] recoverPdf(Long idEdital);

    EditalDTO update(EditalDTO editalDTO);

    void delete(Long idEdital);

    List<EditalDTO> getAll();
}
