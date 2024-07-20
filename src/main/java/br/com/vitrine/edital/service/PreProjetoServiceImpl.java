package br.com.vitrine.edital.service;

import br.com.vitrine.edital.exception.DadoInvalidoException;
import br.com.vitrine.edital.exception.NaoEncontradoException;
import br.com.vitrine.edital.exception.RegistroExistenteException;
import br.com.vitrine.edital.model.dto.PreProjetoDTO;
import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.PreProjeto;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.EditalRepository;
import br.com.vitrine.edital.repository.PreProjetoRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import br.com.vitrine.edital.service.interfaces.PreProjetoService;
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
        validatePreProjetoByEditalAndUsuarioOrThrow(usuario, edital);

        preProjetoDTO.setId(null);
        long idCriado = preProjetoRepository.save(new PreProjeto(preProjetoDTO, usuario, edital)).getId();
        preProjetoDTO.setId(idCriado);

        return preProjetoDTO;
    }

    @Override
    public PreProjetoDTO inserirPdfPreProjetoByUsuarioAndEdital(
            Long idUsuario,
            Long idEdital,
            MultipartFile pdf) {

        try {
            Usuario usuario = getUsuarioOrThrow(idUsuario);
            Edital edital = getEditalOrThrow(idEdital);

            validatePreProjetoByEditalAndUsuarioOrThrow(usuario, edital);
            validarPdf(pdf);


            long idCriado = preProjetoRepository.save(new PreProjeto(usuario, edital, pdf.getBytes())).getId();

            return PreProjetoDTO.builder()
                    .id(idCriado)
                    .idEdital(idEdital)
                    .idUsuario(idUsuario)
                    .build();

        } catch (IOException e) {
            throw new DadoInvalidoException("Erro ao inserir o PDF. " + e.getMessage());
        }
    }

    @Override
    public List<PreProjetoDTO> obterPreprojetosPorUsuario(Long idUsuario) {
        Usuario usuario = getUsuarioOrThrow(idUsuario);
        return preProjetoRepository.findByUsuario(usuario)
                .stream()
                .map(PreProjetoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PreProjetoDTO> obterPreprojetosPorEdital(Long idEdital) {
        Edital edital = getEditalOrThrow(idEdital);
        return preProjetoRepository.findByEdital(edital)
                .stream()
                .map(PreProjetoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
    public byte[] recoverPdf(Long idPreprojeto) {
        PreProjeto preProjeto = getPreProjetoOrThrow(idPreprojeto);
        return preProjeto.getPdf();
    }

    @Override
    public byte[] recoverPdfByUsuarioAndEdital(Long idUsuario, Long idEdital) {
        Edital edital = getEditalOrThrow(idEdital);
        Usuario usuario = getUsuarioOrThrow(idUsuario);

        PreProjeto preProjeto = getPreProjetoByEditalAndUsuarioOrThrow(usuario, edital);
        return preProjeto.getPdf();
    }

    @Override
    public PreProjetoDTO recover(Long idPreProjeto) {
        PreProjeto preProjeto = getPreProjetoOrThrow(idPreProjeto);
        return new PreProjetoDTO(preProjeto);
    }

    @Override
    public PreProjetoDTO update(PreProjetoDTO preProjetoDTO) {
        PreProjeto preProjetoDaBase = getPreProjetoOrThrow(preProjetoDTO.getId());
        Edital edital = getEditalOrThrow(preProjetoDTO.getIdEdital());
        Usuario usuario = getUsuarioOrThrow(preProjetoDTO.getIdUsuario());

        if (preProjetoDaBase.getUsuario().getId() != preProjetoDTO.getIdUsuario()
                || preProjetoDaBase.getEdital().getId() != preProjetoDTO.getIdEdital()) {

            validatePreProjetoByEditalAndUsuarioOrThrow(usuario, edital);
        }


        preProjetoRepository.save(new PreProjeto(preProjetoDTO, usuario, edital, preProjetoDaBase.getPdf()));
        return preProjetoDTO;
    }

    @Override
    public void delete(Long idPreProjeto) {
        getPreProjetoOrThrow(idPreProjeto);
        preProjetoRepository.deleteById(idPreProjeto);
    }

    @Override
    public List<PreProjetoDTO> getAll() {
        return preProjetoRepository.findAll()
                .stream()
                .map(PreProjetoDTO::new)
                .collect(Collectors.toList());
    }

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

    private PreProjeto getPreProjetoByEditalAndUsuarioOrThrow(Usuario usuario, Edital edital) {
        return preProjetoRepository
                .findByEditalAndUsuario(edital, usuario)
                .orElseThrow(() -> new NaoEncontradoException(
                        "Pré Projeto não encontrado. Usuário: " + usuario.getId()
                                + " - Edital: " + edital.getId()));
    }

    private void validatePreProjetoByEditalAndUsuarioOrThrow(Usuario usuario, Edital edital) {
        preProjetoRepository
                .findByEditalAndUsuario(edital, usuario)
                .ifPresent(__ -> {
                    throw new RegistroExistenteException(
                            String.format("Edital %s já possui Pré-Projeto para o usuário %s.", edital.getId(), usuario.getId()));
                });
    }

    private void validarPdf(MultipartFile logo) throws IOException {
        if (isNull(logo) || logo.getBytes().length == 0) {
            throw new DadoInvalidoException("PDF inválido.");
        }
    }

}
