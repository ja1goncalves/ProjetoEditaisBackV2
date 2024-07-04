package br.com.vitrine.edital.service;

import br.com.vitrine.edital.exception.DadoInvalidoException;
import br.com.vitrine.edital.exception.NaoEncontradoException;
import br.com.vitrine.edital.exception.RegistroExistenteException;
import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.model.entity.Edital;
import br.com.vitrine.edital.model.entity.OrgaoFomento;
import br.com.vitrine.edital.model.entity.Usuario;
import br.com.vitrine.edital.repository.EditalRepository;
import br.com.vitrine.edital.repository.OrgaoFomentoRepository;
import br.com.vitrine.edital.repository.PreProjetoRepository;
import br.com.vitrine.edital.repository.UsuarioRepository;
import br.com.vitrine.edital.service.interfaces.EditalService;
import br.com.vitrine.edital.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class EditalServiceImpl implements EditalService {

    private final EditalRepository editalRepository;
    private final PreProjetoRepository preProjetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrgaoFomentoRepository orgaoFomentoRepository;
    private final Utils utils;

    public EditalServiceImpl(
            EditalRepository editalRepository,
            PreProjetoRepository preProjetoRepository,
            UsuarioRepository usuarioRepository,
            OrgaoFomentoRepository orgaoFomentoRepository,
            Utils utils) {

        this.editalRepository = editalRepository;
        this.preProjetoRepository = preProjetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.orgaoFomentoRepository = orgaoFomentoRepository;
        this.utils = utils;
    }

    @Override
    public EditalDTO create(EditalDTO editalDTO) {
        validateEditalDTO(editalDTO, true);
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
    public EditalDTO recover(Long idEdital) {
        Edital edital = getEditalOrThrow(idEdital);
        return new EditalDTO(edital);
    }

    @Override
    public EditalDTO update(EditalDTO editalDTO) {
        validateEditalDTO(editalDTO, false);
        Usuario usuario = getUsuarioOrThrow(editalDTO.getIdUsuario());
        OrgaoFomento orgaoFomento = getOrgaoFomentoOrThrow(editalDTO.getIdOrgaoFomento());

        editalRepository.save(new Edital(editalDTO, usuario, orgaoFomento));
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

    private void validateEditalDTO(EditalDTO editalDTO, boolean isCreate) {
        if (isNull(editalDTO)) {
            throw new DadoInvalidoException("Edital enviado para cadastro inválido");
        }

        if (!utils.isValidString(editalDTO.getNome()) || !utils.isValidString(editalDTO.getDataPublicacao())) {
            throw new DadoInvalidoException("Dados obrigatórios inválidos: Nome/Data Publicação");
        }

        if (isCreate) {
            validateNameEdital(editalDTO.getNome());

        } else {
            Edital edital = getEditalOrThrow(editalDTO.getId());
            if (!editalDTO.getNome().equalsIgnoreCase(edital.getNome())) {
                validateNameEdital(editalDTO.getNome());
            }
        }
    }
}
