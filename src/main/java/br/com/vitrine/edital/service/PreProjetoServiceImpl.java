package br.com.vitrine.edital.service;

import br.com.vitrine.edital.exception.DadoInvalidoException;
import br.com.vitrine.edital.exception.NaoEncontradoException;
import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.model.dto.PreProjetoDTO;
import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.model.entity.PreProjeto;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.EditalRepository;
import br.com.vitrine.edital.repository.PreProjetoRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import br.com.vitrine.edital.service.interfaces.PreProjetoService;
import br.com.vitrine.edital.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class PreProjetoServiceImpl implements PreProjetoService {

    private final PreProjetoRepository preProjetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EditalRepository editalRepository;

    public PreProjetoServiceImpl(
            PreProjetoRepository preProjetoRepository,
            UsuarioRepository usuarioRepository,
            EditalRepository editalRepository) {
        this.preProjetoRepository = preProjetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.editalRepository = editalRepository;
    }

    @Override
    public PreProjetoDTO create(PreProjetoDTO preProjetoDTO) {
        Usuario usuario = getUsuarioOrThrow(preProjetoDTO.getIdUsuario());
        Edital edital = getEditalOrThrow(preProjetoDTO.getIdEdital());

        preProjetoDTO.setId(null);
        PreProjeto novoPreProjeto = new PreProjeto(preProjetoDTO, usuario, edital);
        long idCriado = preProjetoRepository.save(new PreProjeto(preProjetoDTO, usuario, edital)).getId();
        preProjetoDTO.setId(idCriado);

        return preProjetoDTO;
    }

    public void inserirPdfPreProjeto(Long idPreProjeto, MultipartFile pdf) {
        try {
            validarPdf(pdf);
            PreProjeto preprojeto = getPreProjetoOrThrow(idPreProjeto);
            preprojeto.setPdf(pdf.getBytes());
            preProjetoRepository.save(preprojeto);

        } catch (IOException e) {
            throw new DadoInvalidoException("Erro ao inserir o PDF. " + e.getMessage());
        }
    }

    public PreProjetoDTO recover(Long idPreProjeto) {
        PreProjeto preProjeto = getPreProjetoOrThrow(idPreProjeto);
        return new PreProjetoDTO(preProjeto);
    }

    public PreProjetoDTO update(PreProjetoDTO preProjetoDTO) {
        Edital edital = getEditalOrThrow(preProjetoDTO.getIdEdital());
        Usuario usuario = getUsuarioOrThrow(preProjetoDTO.getIdUsuario());

        preProjetoRepository.save(new PreProjeto(preProjetoDTO,usuario,edital));
        return preProjetoDTO;
    }

    public void delete(Long idPreProjeto) {
        getPreProjetoOrThrow(idPreProjeto);
        preProjetoRepository.deleteById(idPreProjeto);
    }

    public List<PreProjetoDTO> getAll() {
         return preProjetoRepository.findAll()
                 .stream()
                 .map(PreProjetoDTO::new)
                 .collect(Collectors.toList());
    }
/*
    public List<PreProjetoDTO> getAllByEdital(Long id_edital){
        return preProjetoRepository.findByIdEdital(id_edital)
                .stream()
                .map(PreProjetoDTO::new)
                .collect(Collectors.toList());
    }

    public List<PreProjetoDTO> findByIdUsuario(Long id_usuario){
        return preProjetoRepository.findByIdUsuario(id_usuario)
                .stream()
                .map(PreProjetoDTO::new)
                .collect(Collectors.toList());
    }
*/
    private Usuario getUsuarioOrThrow(long id) {
        return usuarioRepository
                .findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado: " + id));
    }

    private Edital getEditalOrThrow(long id) {
        return editalRepository
                .findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Edital não encontrado: " + id));
    }

    private PreProjeto getPreProjetoOrThrow(long id) {
        return preProjetoRepository
                .findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Pré Projeto não encontrado: " + id));
    }

    private void validarPdf(MultipartFile logo) throws IOException {
        if (isNull(logo) || logo.getBytes().length == 0) {
            throw new DadoInvalidoException("PDF inválido.");
        }
    }

}
