package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.CredencialDTO;
import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.model.dto.ResponseExceptionDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.service.interfaces.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@Tag(name = "Usuário", description = "Gerencimento de Usuários")
@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Cadastrar um novo usuário",
            description = "Este endpoint tem como objetivo cadastrar um novo usuário no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = UsuarioDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.create(usuarioDTO));

    }

    @Operation(
            summary = "Atualizar um usuário",
            description = "Este endpoint tem como objetivo atualizar um usuário já presente no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UsuarioDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PutMapping
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO = usuarioService.update(usuarioDTO);
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @Operation(
            summary = "Obter um usuário por ID",
            description = "Este endpoint tem como objetivo obter um usuário através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UsuarioDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> recoverById(@PathVariable("id") Long idUsuario) {
        return ResponseEntity.ok().body(usuarioService.recoverById(idUsuario));
    }

    @Operation(
            summary = "Obter um usuário por Login",
            description = "Este endpoint tem como objetivo obter um usuário através do seu Login.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UsuarioDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping(value = "/login/{login}")
    public ResponseEntity<UsuarioDTO> recoverByLogin(@PathVariable("login") String login) {
        return ResponseEntity.ok().body(usuarioService.recoverByLogin(login));
    }

    @Operation(
            summary = "Deletar um usuário por ID",
            description = "Este endpoint tem como objetivo deletar um usuário através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Obter todos os usuários",
            description = "Este endpoint tem como objetivo obter todos os usuários cadastrados no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok().body(usuarioService.getAll());
    }

    @Operation(
            summary = "Realizar login na aplicação",
            description = "Este endpoint tem como objetivo reaizar a validação do usuário e senha enviados na requisição com os que estão cadastrados no banco.",
            tags = {"Login"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UsuarioDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping(value = "/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody CredencialDTO credencialDTO) {
        return ResponseEntity.ok().body(usuarioService.login(credencialDTO));
    }

    @Operation(
            summary = "Obter todos os editais favoritados pelo usuário",
            description = "Este endpoint tem como objetivo obter todos os editais favoritos do usuário consultado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = EditalDTO.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/{idUsuario}/editais-favoritos")
    public ResponseEntity<Set<EditalDTO>> getEditaisFavoritos(@PathVariable Long idUsuario) {
        Set<EditalDTO> editais = usuarioService.getEditaisFavoritos(idUsuario);
        return ResponseEntity.ok(editais);
    }


}
