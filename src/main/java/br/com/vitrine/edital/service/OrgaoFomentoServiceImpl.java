package br.com.vitrine.edital.service;

import br.com.vitrine.edital.exception.DadoInvalidoException;
import br.com.vitrine.edital.exception.NaoEncontradoException;
import br.com.vitrine.edital.exception.RegistroExistenteException;
import br.com.vitrine.edital.exception.UsuarioException;
import br.com.vitrine.edital.model.dto.OrgaoFomentoDTO;
import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.repository.OrgaoFomentoRepository;
import br.com.vitrine.edital.service.interfaces.OrgaoFomentoService;
import br.com.vitrine.edital.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class OrgaoFomentoServiceImpl implements OrgaoFomentoService {

    private final OrgaoFomentoRepository orgaoFomentoRepository;
    private final Utils utils;

    public OrgaoFomentoServiceImpl(OrgaoFomentoRepository orgaoFomentoRepository, Utils utils) {
        this.orgaoFomentoRepository = orgaoFomentoRepository;
        this.utils = utils;
    }

    @Override
    public OrgaoFomentoDTO create(OrgaoFomentoDTO orgaoFomentoDTO) {
        if (isNull(orgaoFomentoDTO)) {
            throw new DadoInvalidoException("Órgão Fomento enviado para cadastro inválido");
        }

        if (!utils.isValidString(orgaoFomentoDTO.getNome())) {
            throw new DadoInvalidoException("Nome do órgão de fomento está  inválido");
        }

        validateName(orgaoFomentoDTO.getNome());
        orgaoFomentoDTO.setId(null);
        long idCriado = orgaoFomentoRepository
                .save(new OrgaoFomento(orgaoFomentoDTO))
                .getId();

        orgaoFomentoDTO.setId(idCriado);
        return orgaoFomentoDTO;
    }

    @Override
    public OrgaoFomentoDTO recover(Long idOrgaoFomento) {
        return new OrgaoFomentoDTO(getOrgaoFomentoOrThrow(idOrgaoFomento));
    }

    @Override
    public OrgaoFomentoDTO update(OrgaoFomentoDTO orgaoFomentoDTO) {
        validateOrgaoFomentoToUpdate(orgaoFomentoDTO);
        orgaoFomentoRepository.save(new OrgaoFomento(orgaoFomentoDTO));
        return orgaoFomentoDTO;
    }

    @Override
    public void delete(Long idOrgaoFomento) {
        getOrgaoFomentoOrThrow(idOrgaoFomento);
        orgaoFomentoRepository.deleteById(idOrgaoFomento);
    }

    @Override
    public List<OrgaoFomentoDTO> getAll() {
        return orgaoFomentoRepository.findAllByOrderByNomeAsc()
                .stream()
                .map(OrgaoFomentoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public OrgaoFomentoDTO recoverByName(String nomeOrgaoFomento) {
        return new OrgaoFomentoDTO(getOrgaoFomentoByNameOrThrow(nomeOrgaoFomento));
    }

    private OrgaoFomento getOrgaoFomentoOrThrow(long idOrgaoFomento) {
        return orgaoFomentoRepository
                .findById(idOrgaoFomento)
                .orElseThrow(() -> new NaoEncontradoException("Órgão de fomento não encontrado: " + idOrgaoFomento));
    }

    private OrgaoFomento getOrgaoFomentoByNameOrThrow(String nomeOrgaoFomento) {
        return orgaoFomentoRepository
                .findByNomeIgnoreCase(nomeOrgaoFomento)
                .orElseThrow(() -> new NaoEncontradoException("Órgão de fomento não encontrado: " + nomeOrgaoFomento));
    }

    private void validateName(String nome) {
        orgaoFomentoRepository
                .findByNomeIgnoreCase(nome)
                .ifPresent(__ -> {
                    throw new RegistroExistenteException(String.format("Órgão de fomento '%s' já cadastrado.", nome));
                });
    }

    private void validateOrgaoFomentoToUpdate(OrgaoFomentoDTO orgaoFomentoDTO) {
        if (isNull(orgaoFomentoDTO)) {
            throw new DadoInvalidoException("Órgão Fomento enviado para cadastro inválido");
        }

        if (isNull(orgaoFomentoDTO.getId()) || orgaoFomentoDTO.getId() == 0) {
            throw new UsuarioException("Id do órgão de fomento está inválido: " + orgaoFomentoDTO.getId());
        }

        OrgaoFomento orgaoFomento = getOrgaoFomentoOrThrow(orgaoFomentoDTO.getId());
        if (!utils.isValidString(orgaoFomentoDTO.getNome())) {
            throw new DadoInvalidoException("Nome do órgão de fomento está  inválido");
        }

        if (!orgaoFomentoDTO.getNome().equalsIgnoreCase(orgaoFomento.getNome())) {
            validateName(orgaoFomentoDTO.getNome());
        }
    }
}
