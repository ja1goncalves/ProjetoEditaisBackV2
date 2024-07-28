/*package br.com.vitrine.edital.controller;

public class PreProjetoController {
}
*/

package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.PreProjetoDTO;
import br.com.vitrine.edital.model.dto.ResponseExceptionDTO;
import br.com.vitrine.edital.service.interfaces.PreProjetoService;
import br.com.vitrine.edital.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Tag(name = "Pré-Projeto", description = "Gerenciamento de Pré-Projetos")
@RestController
@RequestMapping(path = "/preprojeto")
public class PreProjetoController {

    private final PreProjetoService preProjetoService;
    private final Utils utils;

    public PreProjetoController(PreProjetoService preProjetoService, Utils utils) {
        this.preProjetoService = preProjetoService;
        this.utils = utils;
    }

    @Operation(
            summary = "Cadastrar um novo pré-projeto",
            description = "Este endpoint tem como objetivo cadastrar um novo pré-projeto no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = PreProjetoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping
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
        preProjetoService.inserirPdfPreProjeto(idPreProjeto, pdf);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Cadastrar um novo pré-projeto para um edital - vinculando ao usuário",
            description = "Este endpoint tem como objetivo cadastrar um novo pré-projeto no banco de dados - vinculo a Edital e Usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = PreProjetoDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping(value = "/usuario/{idUsuario}/edital/{idEdital}")
    public ResponseEntity<PreProjetoDTO> create(
            @PathVariable("idUsuario") Long idUsuario,
            @PathVariable("idEdital") Long idEdital,
            @RequestParam("preprojeto_pdf") MultipartFile pdf) {

        PreProjetoDTO novoPreProjeto = preProjetoService.inserirPdfPreProjetoByUsuarioAndEdital(idUsuario, idEdital, pdf);
        return new ResponseEntity<>(novoPreProjeto, HttpStatus.CREATED);
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
            summary = "Obter o PDF do Pré-Projeto por ID",
            description = "Este endpoint tem como objetivo obter o PDF do Pré-Projeto através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE
            )),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/{idPreProjeto}/pdf")
    public ResponseEntity<byte[]> obterPdf(@PathVariable("idPreProjeto") Long idPreProjeto) {
        byte[] pdf = preProjetoService.recoverPdf(idPreProjeto);
        if (!utils.isValidByteArray(pdf)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        String nomeArquivo = String.format("Pre-Projeto_" + idPreProjeto);
        HttpHeaders header = getHeaderResponseStream(pdf.length, nomeArquivo);
        return ResponseEntity.ok().headers(header).body(pdf);
    }

    @Operation(
            summary = "Obter o PDF do Pré-Projeto através do ID do Usuário e ID do Edital",
            description = "Este endpoint tem como objetivo obter o PDF do Pré-Projeto através do ID do Usuário e ID do Edital")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE
            )),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/usuario/{idUsuario}/edital/{idEdital}/pdf")
    public ResponseEntity<byte[]> obterPdfByUsuarioAndEdital(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idEdital") Long idEdital) {
        byte[] pdf = preProjetoService.recoverPdfByUsuarioAndEdital(idUsuario, idEdital);
        if (!utils.isValidByteArray(pdf)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        String nomeArquivo = String.format("Pre-Projeto_" + idUsuario + "_" + idEdital);
        HttpHeaders header = getHeaderResponseStream(pdf.length, nomeArquivo);
        return ResponseEntity.ok().headers(header).body(pdf);
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
            summary = "Obter todos os pré-projetos por usuário",
            description = "Este endpoint tem como objetivo obter todos os pré-projetos cadastrados por usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PreProjetoDTO.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PreProjetoDTO>> getAllByUser(@PathVariable("idUsuario") Long idUsuario) {
        List<PreProjetoDTO> preProjetos = preProjetoService.obterPreprojetosPorUsuario(idUsuario);
        return ResponseEntity.ok(preProjetos);
    }

    @Operation(
            summary = "Obter todos os pré-projetos por edital",
            description = "Este endpoint tem como objetivo obter todos os pré-projetos cadastrados por edital.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = PreProjetoDTO.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/edital/{idEdital}")
    public ResponseEntity<List<PreProjetoDTO>> getAllByEdital(@PathVariable("idEdital") Long idEdital) {
        List<PreProjetoDTO> preProjetos = preProjetoService.obterPreprojetosPorEdital(idEdital);
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
    @PutMapping
    public ResponseEntity<PreProjetoDTO> update(@RequestBody PreProjetoDTO preProjetoDTO) {
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

    private HttpHeaders getHeaderResponseStream(long tamanhoArquivo, String nomeArquivo) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.setContentLength(tamanhoArquivo);
        header.set("Content-Disposition", String.format("attachment; filename=%s.pdf", nomeArquivo));
        return header;
    }

}
