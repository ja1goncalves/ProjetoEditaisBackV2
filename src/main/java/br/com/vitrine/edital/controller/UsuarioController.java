package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.service.interfaces.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.create(usuarioDTO));

    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO = usuarioService.update(usuarioDTO);
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity recover(@PathVariable("id") Long idUsuario) {
        return ResponseEntity.ok().body(usuarioService.recover(idUsuario));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok().body(usuarioService.getAll());
    }

}
