package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.EditalDTO;
import br.com.vitrine.edital.model.dto.ResponseExceptionDTO;
import br.com.vitrine.edital.model.dto.UsuarioDTO;
import br.com.vitrine.edital.service.interfaces.EditalService;
import br.com.vitrine.edital.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Tag(name = "Edital", description = "Gerencimento de Editais")
@RestController
@RequestMapping(path = "/edital")
public class EditalController {

    private final EditalService editalService;
    private final Utils utils;

    public EditalController(EditalService editalService, Utils utils) {
        this.editalService = editalService;
        this.utils = utils;
    }

    @Operation(
            summary = "Cadastrar um novo edital",
            description = "Este endpoint tem como objetivo cadastrar um novo edital no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = EditalDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping
    public ResponseEntity<EditalDTO> create(@RequestBody EditalDTO editalDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(editalService.create(editalDTO));

    }

    @Operation(
            summary = "Inserir o PDF do Edital",
            description = "Este endpoint tem como objetivo inserir o PDF do edital através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping(value = "/inserir/{idEdital}/pdf")
    public ResponseEntity inserirPdf(@PathVariable("idEdital") Long idEdital, @RequestParam("edital_pdf") MultipartFile pdf) {
        editalService.inserirPdf(idEdital, pdf);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Obter o PDF do Edital",
            description = "Este endpoint tem como objetivo obter o PDF do edital através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE
            )),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/{idEdital}/pdf")
    public ResponseEntity<byte[]> obterPdf(@PathVariable("idEdital") Long idEdital) {
        byte[] pdf = editalService.recoverPdf(idEdital);
        if (!utils.isValidByteArray(pdf)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        String nomeArquivo = String.format("Edital_" + idEdital);
        HttpHeaders header = getHeaderResponseStream(pdf.length, nomeArquivo);
        return ResponseEntity.ok().headers(header).body(pdf);
    }

    @Operation(
            summary = "Atualizar um Edital",
            description = "Este endpoint tem como objetivo atualizar um edital já presente no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = EditalDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PutMapping
    public ResponseEntity<EditalDTO> update(@RequestBody EditalDTO editalDTO) {
        editalDTO = editalService.update(editalDTO);
        return ResponseEntity.ok().body(editalDTO);
    }

    @Operation(
            summary = "Obter um edital por ID",
            description = "Este endpoint tem como objetivo obter um edital através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = EditalDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping(value = "/{id}")
    public ResponseEntity<EditalDTO> recover(@PathVariable("id") Long idEdital) {
        return ResponseEntity.ok().body(editalService.recover(idEdital));
    }

    @Operation(
            summary = "Deletar um edital por ID",
            description = "Este endpoint tem como objetivo deletar um edital através do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long idEdital) {
        editalService.delete(idEdital);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Obter todos os editais",
            description = "Este endpoint tem como objetivo obter todos os editais cadastrados no banco de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = EditalDTO.class)))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping
    public ResponseEntity<List<EditalDTO>> getAll() {
        return ResponseEntity.ok().body(editalService.getAll());
    }

    @Operation(
            summary = "Favoritar um edital",
            description = "Este endpoint tem como objetivo favoritar um edital pelo usuário, o adicionando da tabela EDITAL_FAVORITO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @PostMapping(value = "/{idEdital}/usuario/{idUsuario}/favoritar")
    public ResponseEntity favoritarEdital(@PathVariable("idEdital") Long idEdital, @PathVariable("idUsuario") Long idUsuario) {
        editalService.favoritarEdital(idEdital, idUsuario);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Desfavoritar um edital",
            description = "Este endpoint tem como objetivo desfavoritar um edital pelo usuário, o removendo da tabela EDITAL_FAVORITO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @DeleteMapping(value = "/{idEdital}/usuario/{idUsuario}/desfavoritar")
    public ResponseEntity desfavoritarEdital(@PathVariable("idEdital") Long idEdital, @PathVariable("idUsuario") Long idUsuario) {
        editalService.desfavoritarEdital(idEdital, idUsuario);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Obter todos os usuários que favoritaram o edital",
            description = "Este endpoint tem como objetivo obter todos os usuários que favoritaram o edital consultado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    //@PostMapping(value = "/{idEdital}/usuario/{idUsuario}/favoritar")
    @GetMapping("/{idEdital}/usuarios-que-favoritaram")
    public ResponseEntity<Set<UsuarioDTO>> getUsuariosQueFavoritaram(@PathVariable Long idEdital) {
        Set<UsuarioDTO> usuarios = editalService.getUsuariosQueFavoritaram(idEdital);
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
            summary = "Obter todos os editais por usuário",
            description = "Este endpoint tem como objetivo obter todos os editais que foram criados pelo BOT e pelo usuário passado como parâmetro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = EditalDTO.class)))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EditalDTO>> getEditaisPorUsuario(@PathVariable Long idUsuario) {
        List<EditalDTO> editalDTOS = editalService.getEditalByUserFilterAndBot(idUsuario);
        return ResponseEntity.ok(editalDTOS);
    }

    private HttpHeaders getHeaderResponseStream(long tamanhoArquivo, String nomeArquivo) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.setContentLength(tamanhoArquivo);
        header.set("Content-Disposition", String.format("attachment; filename=%s.pdf", nomeArquivo));
        return header;
    }


}
