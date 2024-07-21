package br.com.vitrine.edital.service;

import br.com.vitrine.edital.exception.DadoInvalidoException;
import br.com.vitrine.edital.exception.NaoEncontradoException;
import br.com.vitrine.edital.exception.RegistroExistenteException;
import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.EditalRepository;
import br.com.vitrine.edital.repository.OrgaoFomentoRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import br.com.vitrine.edital.service.interfaces.EditalService;
import br.com.vitrine.edital.service.interfaces.UsuarioService;
import br.com.vitrine.edital.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class EditalServiceImpl implements EditalService {

    private final EditalRepository editalRepository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final OrgaoFomentoRepository orgaoFomentoRepository;
    private final Utils utils;

    public EditalServiceImpl(
            EditalRepository editalRepository,
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            OrgaoFomentoRepository orgaoFomentoRepository,
            Utils utils) {

        this.editalRepository = editalRepository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.orgaoFomentoRepository = orgaoFomentoRepository;
        this.utils = utils;
    }

    @Override
    public EditalDTO create(EditalDTO editalDTO) {
        validateEditalDTO(editalDTO);
        validateNameEdital(editalDTO.getNome());

        Usuario usuario = getUsuarioOrThrow(editalDTO.getIdUsuario());
        OrgaoFomento orgaoFomento = getOrgaoFomentoOrThrow(editalDTO.getIdOrgaoFomento());

        editalDTO.setId(null);
        editalDTO.setCriadoPorBot(usuario.getLogin().equalsIgnoreCase("BOT"));
        long idCriado = editalRepository.save(new Edital(editalDTO, usuario, orgaoFomento)).getId();
        editalDTO.setId(idCriado);

        editalDTO.setCriadoPorBot(usuario.getLogin().equalsIgnoreCase("BOT"));
        return editalDTO;
    }

    @Override
    public void inserirPdf(Long idEdital, MultipartFile pdf) {
        try {
            validarPdf(pdf);
            Edital edital = getEditalOrThrow(idEdital);
            edital.setPdf(pdf.getBytes());
            editalRepository.save(edital);

        } catch (IOException e) {
            throw new DadoInvalidoException("Erro ao inserir o PDF. " + e.getMessage());
        }

    }

    @Override
    public EditalDTO recover(Long idEdital) {
        Edital edital = getEditalOrThrow(idEdital);
        return new EditalDTO(edital);
    }

    @Override
    public byte[] recoverPdf(Long idEdital) {
        Edital edital = getEditalOrThrow(idEdital);
        return edital.getPdf();
    }

    @Override
    public EditalDTO update(EditalDTO editalDTO) {
        validateEditalDTO(editalDTO);
        Usuario usuario = getUsuarioOrThrow(editalDTO.getIdUsuario());
        OrgaoFomento orgaoFomento = getOrgaoFomentoOrThrow(editalDTO.getIdOrgaoFomento());

        Edital editalDaBase = getEditalOrThrow(editalDTO.getId());
        if (!editalDTO.getNome().equalsIgnoreCase(editalDaBase.getNome())) {
            validateNameEdital(editalDTO.getNome());
        }

        editalDaBase.atualizarEntidade(editalDTO, usuario, orgaoFomento);
        editalRepository.save(editalDaBase);
        return editalDTO;
    }

    @Override
    public void delete(Long idEdital) {
        getEditalOrThrow(idEdital);
        editalRepository.deleteById(idEdital);
    }

    @Override
    public List<EditalDTO> getAll() {
        return editalRepository.findAllByOrderByNomeAsc()
                .stream()
                .map(EditalDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public void favoritarEdital(Long idEdital, Long idUsuario) {
        Edital edital = getEditalOrThrow(idEdital);
        Usuario usuario = getUsuarioOrThrow(idUsuario);

        edital.getUsuariosQueFavoritaram().add(usuario);
        usuario.getEditaisFavoritos().add(edital);

        editalRepository.save(edital);
    }

    @Override
    public void desfavoritarEdital(Long idEdital, Long idUsuario) {
        Edital edital = getEditalOrThrow(idEdital);
        Usuario usuario = getUsuarioOrThrow(idUsuario);

        edital.getUsuariosQueFavoritaram().remove(usuario);
        usuario.getEditaisFavoritos().remove(edital);

        editalRepository.save(edital);
    }

    @Override
    public Set<UsuarioDTO> getUsuariosQueFavoritaram(Long idEdital) {
        getEditalOrThrow(idEdital);
        return editalRepository.findUsuariosQueFavoritaramByIdEdital(idEdital)
                .stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toSet());
    }

    @Override
    public List<EditalDTO> getEditalByUserFilterAndBot(Long idUsuario) {
        getUsuarioOrThrow(idUsuario);
        UsuarioDTO usuarioDTO = usuarioService.recoverByLogin("Bot");
        List<Long> idsUsuarios = List.of(idUsuario, usuarioDTO.getId());

        return editalRepository.findByUsers(idsUsuarios)
                .stream()
                .map(EditalDTO::new)
                .collect(Collectors.toList());
    }

    private void validateNameEdital(String nome) {
        editalRepository
                .findByNome(nome)
                .ifPresent(__ -> {
                    throw new RegistroExistenteException(String.format("Nome do edital '%s' já cadastrado em outro registro.", nome));
                });
    }

    private Usuario getUsuarioOrThrow(long idUsuario) {
        return usuarioRepository
                .findById(idUsuario)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado: " + idUsuario));
    }

    private OrgaoFomento getOrgaoFomentoOrThrow(long idOrgaoFomento) {
        return orgaoFomentoRepository
                .findById(idOrgaoFomento)
                .orElseThrow(() -> new NaoEncontradoException("Órgão de fomento não encontrado: " + idOrgaoFomento));
    }

    private Edital getEditalOrThrow(long idEdital) {
        return editalRepository
                .findById(idEdital)
                .orElseThrow(() -> new NaoEncontradoException("Edital não encontrado: " + idEdital));
    }

    private void validateEditalDTO(EditalDTO editalDTO) {
        if (isNull(editalDTO)) {
            throw new DadoInvalidoException("Edital enviado para cadastro inválido");
        }

        if (!utils.isValidString(editalDTO.getNome()) || !utils.isValidString(editalDTO.getDataPublicacao())) {
            throw new DadoInvalidoException("Dados obrigatórios inválidos: Nome/Data Publicação");
        }
    }

    private void validarPdf(MultipartFile logo) throws IOException {
        if (isNull(logo) || logo.getBytes().length == 0) {
            throw new DadoInvalidoException("PDF inválido.");
        }
    }
}
