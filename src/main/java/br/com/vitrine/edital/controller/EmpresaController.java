package br.com.vitrine.edital.controller;

import br.com.vitrine.edital.model.dto.EmpresaDTO;
import br.com.vitrine.edital.model.dto.OrgaoFomentoDTO;
import br.com.vitrine.edital.model.dto.ResponseExceptionDTO;
import br.com.vitrine.edital.model.entity.Empresa;
import br.com.vitrine.edital.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "Empresa", description = "Gerenciamento de Empresas")
@RestController
@RequestMapping(path = "/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;
    EmpresaController(EmpresaService empresaService ){
        this.empresaService = empresaService;
    }
    

    // Endpoint para criar ou atualizar uma empresa
    @PostMapping
    public ResponseEntity<Empresa> createOrUpdateEmpresa(@RequestBody Empresa empresa) {
        Empresa savedEmpresa = empresaService.saveEmpresa(empresa);
        return ResponseEntity.ok(savedEmpresa);
    }

    // Endpoint para buscar uma empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.getEmpresaById(id);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para buscar todas as empresas
    @Operation(
            summary = "Get all empresas",
            description = "Este endpoint tem como objetivo cadastrar um novo órgão de fomento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = EmpresaDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "422", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseExceptionDTO.class), mediaType = "application/json")})})
    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }

    // Endpoint para deletar uma empresa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}