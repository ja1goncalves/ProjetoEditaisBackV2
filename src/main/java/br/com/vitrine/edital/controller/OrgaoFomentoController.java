package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.OrgaoFomentoDTO;
import br.com.vitrine.edital.model.dto.ResponseExceptionDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.service.interfaces.OrgaoFomentoService;
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

@Slf4j
@Tag(name = "Órgão Fomento", description = "Gerencimento de Órgãos de Formento")
@RestController
@RequestMapping(path = "/orgaoFomento")
public class OrgaoFomentoController {

    private final OrgaoFomentoService orgaoFomentoService;

    public OrgaoFomentoController(OrgaoFomentoService orgaoFomentoService) {
        this.orgaoFomentoService = orgaoFomentoService;
    }

    @Operation(
            summary = "Cadastrar um novo órgão de fomento",
            description = "Este endpoint tem como objetivo cadastrar um novo órgão de fomento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = OrgaoFomentoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping
    public ResponseEntity<OrgaoFomentoDTO> create(@RequestBody OrgaoFomentoDTO orgaoFomentoDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orgaoFomentoService.create(orgaoFomentoDTO));

    }

    @Operation(
            summary = "Atualizar um órgão de fomento",
            description = "Este endpoint tem como objetivo atualizar um órgão de fomento já presente no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OrgaoFomentoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PutMapping
    public ResponseEntity<OrgaoFomentoDTO> update(@RequestBody OrgaoFomentoDTO orgaoFomentoDTO) {
        orgaoFomentoDTO = orgaoFomentoService.update(orgaoFomentoDTO);
        return ResponseEntity.ok().body(orgaoFomentoDTO);
    }

    @Operation(
            summary = "Obter um órgão de fomento por ID",
            description = "Este endpoint tem como objetivo obter um órgão de fomento através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OrgaoFomentoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrgaoFomentoDTO> recover(@PathVariable("id") Long idOrgaoFomento) {
        return ResponseEntity.ok().body(orgaoFomentoService.recover(idOrgaoFomento));
    }

    @Operation(
            summary = "Obter um órgão de fomento por seu nome",
            description = "Este endpoint tem como objetivo obter um órgão de fomento através do seu nome.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = OrgaoFomentoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<OrgaoFomentoDTO> recoverByName(@PathVariable("nome") String nomeOrgaoFomento) {
        return ResponseEntity.ok().body(orgaoFomentoService.recoverByName(nomeOrgaoFomento));
    }

    @Operation(
            summary = "Deletar um órgão de fomento por ID",
            description = "Este endpoint tem como objetivo deletar um órgão de fomento através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long idOrgaoFomento) {
        orgaoFomentoService.delete(idOrgaoFomento);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Obter todos os órgãos de fomento",
            description = "Este endpoint tem como objetivo obter todos os órgãos de fomento cadastrados no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping
    public ResponseEntity<List<OrgaoFomentoDTO>> getAll() {
        return ResponseEntity.ok().body(orgaoFomentoService.getAll());
    }

}
