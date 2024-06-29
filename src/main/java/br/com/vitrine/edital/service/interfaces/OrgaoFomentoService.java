package br.com.vitrine.edital.service.interfaces;

import br.com.vitrine.edital.model.dto.OrgaoFomentoDTO;

import java.util.List;

public interface OrgaoFomentoService {

    OrgaoFomentoDTO create(OrgaoFomentoDTO orgaoFomentoDTO);

    OrgaoFomentoDTO recover(Long idOrgaoFomento);

    OrgaoFomentoDTO update(OrgaoFomentoDTO orgaoFomentoDTO);

    void delete(Long idOrgaoFomento);

    List<OrgaoFomentoDTO> getAll();

    OrgaoFomentoDTO recoverByName(String nomeOrgaoFomento);
}
