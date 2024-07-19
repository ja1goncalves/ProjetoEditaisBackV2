/*package br.com.vitrine.edital.controller;

public class PreProjetoController {
}
*/

package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.PreProjetoDTO;
import br.com.vitrine.edital.model.dto.ResponseExceptionDTO;
import br.com.vitrine.edital.service.interfaces.PreProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "PreProjeto", description = "Gerenciamento de Pré-Projetos")
@RestController
@RequestMapping(path = "/preprojeto")
public class PreProjetoController {

    private final PreProjetoService preProjetoService;

    public PreProjetoController(PreProjetoService preProjetoService) {
        this.preProjetoService = preProjetoService;
    }

    @Operation(
            summary = "Cadastrar um novo pré-projeto para um edital - vinculando ao usuário",
            description = "Este endpoint tem como objetivo cadastrar um novo pré-projeto no banco de dados - vinculo a Edital e Usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = PreProjetoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping(value = "{idEdital}/usuario/{idUsuario}/inserir")
    public ResponseEntity<PreProjetoDTO> create(@RequestBody PreProjetoDTO preProjetoDTO) {
        PreProjetoDTO novoPreProjeto = preProjetoService.create(preProjetoDTO);
        return new ResponseEntity<>(novoPreProjeto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Inserir o PDF do Pré-projeto",
            description = "Este endpoint tem como objetivo inserir o PDF do pré-projeto para determinado edital.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping(value = "/inserir/{idPreProjeto}/pdf")
    public ResponseEntity inserirPdf(@PathVariable("idPreProjeto") Long idPreProjeto, @RequestParam("preprojeto_pdf") MultipartFile pdf) {
           preProjetoService.inserirPdfPreProjeto(idPreProjeto,pdf);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Obter um pré-projeto por ID",
            description = "Este endpoint tem como objetivo obter um pré-projeto pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PreProjetoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/{id}")
    public ResponseEntity<PreProjetoDTO> recover(@PathVariable Long id) {
        PreProjetoDTO preProjetoDTO = preProjetoService.recover(id);
        return ResponseEntity.ok(preProjetoDTO);
    }

    @Operation(
            summary = "Obter todos os pré-projetos",
            description = "Este endpoint tem como objetivo obter todos os pré-projetos cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PreProjetoDTO.class)))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping
    public ResponseEntity<List<PreProjetoDTO>> getAll() {
        List<PreProjetoDTO> preProjetos = preProjetoService.getAll();
        return ResponseEntity.ok(preProjetos);
    }

    @Operation(
            summary = "Atualizar um pré-projeto",
            description = "Este endpoint tem como objetivo atualizar um pré-projeto existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PreProjetoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PutMapping("/{id}")
    public ResponseEntity<PreProjetoDTO> update(@PathVariable Long id, @RequestBody PreProjetoDTO preProjetoDTO) {
        PreProjetoDTO preProjetoAtualizado = preProjetoService.update(preProjetoDTO);
        return ResponseEntity.ok(preProjetoAtualizado);
    }

    @Operation(
            summary = "Deletar um pré-projeto",
            description = "Este endpoint tem como objetivo deletar um pré-projeto existente pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        preProjetoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
