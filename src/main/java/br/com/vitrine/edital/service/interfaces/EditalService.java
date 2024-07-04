package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.EditalDTO;

import java.util.List;

public interface EditalService {

    EditalDTO create(EditalDTO editalDTO);

    EditalDTO recover(Long idEdital);

    EditalDTO update(EditalDTO editalDTO);

    void delete(Long idEdital);

    List<EditalDTO> getAll();
}
