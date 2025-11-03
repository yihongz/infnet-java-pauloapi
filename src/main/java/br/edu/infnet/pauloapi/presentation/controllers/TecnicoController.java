package br.edu.infnet.pauloapi.presentation.controllers;

import br.edu.infnet.pauloapi.application.dto.TecnicoDTO;
import br.edu.infnet.pauloapi.application.service.TecnicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
@RequiredArgsConstructor
@Tag(name = "Técnicos", description = "API para gerenciamento de técnicos")
public class TecnicoController {
    
    private final TecnicoService tecnicoService;
    
    @PostMapping
    @Operation(summary = "Criar novo técnico", description = "Cria um novo técnico no sistema")
    public ResponseEntity<TecnicoDTO> criar(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
        TecnicoDTO tecnicoCriado = tecnicoService.criar(tecnicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoCriado);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os técnicos", description = "Retorna lista de todos os técnicos")
    public ResponseEntity<List<TecnicoDTO>> listarTodos() {
        List<TecnicoDTO> tecnicos = tecnicoService.listarTodos();
        return ResponseEntity.ok(tecnicos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar técnico por ID", description = "Retorna um técnico específico pelo ID")
    public ResponseEntity<TecnicoDTO> buscarPorId(@PathVariable Long id) {
        TecnicoDTO tecnico = tecnicoService.buscarPorId(id);
        return ResponseEntity.ok(tecnico);
    }
    
    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Buscar técnico por matrícula", description = "Retorna um técnico específico pela matrícula")
    public ResponseEntity<TecnicoDTO> buscarPorMatricula(@PathVariable String matricula) {
        TecnicoDTO tecnico = tecnicoService.buscarPorMatricula(matricula);
        return ResponseEntity.ok(tecnico);
    }
    
    @GetMapping("/especialidade/{especialidade}")
    @Operation(summary = "Buscar técnicos por especialidade", description = "Retorna técnicos com a especialidade especificada")
    public ResponseEntity<List<TecnicoDTO>> buscarPorEspecialidade(@PathVariable String especialidade) {
        List<TecnicoDTO> tecnicos = tecnicoService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(tecnicos);
    }
    
    @GetMapping("/ativos")
    @Operation(summary = "Listar técnicos ativos", description = "Retorna lista de técnicos ativos")
    public ResponseEntity<List<TecnicoDTO>> listarAtivos() {
        List<TecnicoDTO> tecnicos = tecnicoService.listarAtivos();
        return ResponseEntity.ok(tecnicos);
    }
    
    @GetMapping("/disponiveis")
    @Operation(summary = "Listar técnicos disponíveis", description = "Retorna técnicos disponíveis (sem OS em andamento)")
    public ResponseEntity<List<TecnicoDTO>> listarDisponiveis() {
        List<TecnicoDTO> tecnicos = tecnicoService.listarDisponiveis();
        return ResponseEntity.ok(tecnicos);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar técnico", description = "Atualiza os dados de um técnico existente")
    public ResponseEntity<TecnicoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TecnicoDTO tecnicoDTO) {
        TecnicoDTO tecnicoAtualizado = tecnicoService.atualizar(id, tecnicoDTO);
        return ResponseEntity.ok(tecnicoAtualizado);
    }
    
    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar técnico", description = "Marca um técnico como inativo")
    public ResponseEntity<TecnicoDTO> inativar(@PathVariable Long id) {
        TecnicoDTO tecnicoInativado = tecnicoService.inativar(id);
        return ResponseEntity.ok(tecnicoInativado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar técnico", description = "Remove um técnico do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecnicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
