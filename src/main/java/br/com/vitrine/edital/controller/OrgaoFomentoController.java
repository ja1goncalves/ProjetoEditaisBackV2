package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.OrgaoFomentoDTO;
import br.com.vitrine.edital.service.interfaces.OrgaoFomentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/orgaoFomento")
public class OrgaoFomentoController {

    private final OrgaoFomentoService orgaoFomentoService;

    public OrgaoFomentoController(OrgaoFomentoService orgaoFomentoService) {
        this.orgaoFomentoService = orgaoFomentoService;
    }

    @PostMapping
    public ResponseEntity<OrgaoFomentoDTO> create(@RequestBody OrgaoFomentoDTO orgaoFomentoDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orgaoFomentoService.create(orgaoFomentoDTO));

    }

    @PutMapping
    public ResponseEntity<OrgaoFomentoDTO> update(@RequestBody OrgaoFomentoDTO orgaoFomentoDTO) {
        orgaoFomentoDTO = orgaoFomentoService.update(orgaoFomentoDTO);
        return ResponseEntity.ok().body(orgaoFomentoDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrgaoFomentoDTO> recover(@PathVariable("id") Long idOrgaoFomento) {
        return ResponseEntity.ok().body(orgaoFomentoService.recover(idOrgaoFomento));
    }

    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<OrgaoFomentoDTO> recoverByName(@PathVariable("nome") String nomeOrgaoFomento) {
        return ResponseEntity.ok().body(orgaoFomentoService.recoverByName(nomeOrgaoFomento));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long idOrgaoFomento) {
        orgaoFomentoService.delete(idOrgaoFomento);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<OrgaoFomentoDTO>> getAll() {
        return ResponseEntity.ok().body(orgaoFomentoService.getAll());
    }

}
