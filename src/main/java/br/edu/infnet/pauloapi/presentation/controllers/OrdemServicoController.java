package br.edu.infnet.pauloapi.presentation.controllers;

import br.edu.infnet.pauloapi.application.dto.*;
import br.edu.infnet.pauloapi.application.service.OrdemServicoService;
import br.edu.infnet.pauloapi.domain.model.StatusOS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens-servico")
@RequiredArgsConstructor
@Tag(name = "Ordens de Serviço", description = "API para gerenciamento de ordens de serviço")
public class OrdemServicoController {
    
    private final OrdemServicoService ordemServicoService;
    
    @PostMapping("/manutencao")
    @Operation(summary = "Criar OS de Manutenção", description = "Cria uma nova ordem de serviço de manutenção")
    public ResponseEntity<OrdemServicoDTO> criarManutencao(@Valid @RequestBody OSManutencaoDTO dto) {
        OrdemServicoDTO osCriada = ordemServicoService.criarManutencao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(osCriada);
    }
    
    @PostMapping("/instalacao")
    @Operation(summary = "Criar OS de Instalação", description = "Cria uma nova ordem de serviço de instalação")
    public ResponseEntity<OrdemServicoDTO> criarInstalacao(@Valid @RequestBody OSInstalacaoDTO dto) {
        OrdemServicoDTO osCriada = ordemServicoService.criarInstalacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(osCriada);
    }
    
    @PostMapping("/consultoria")
    @Operation(summary = "Criar OS de Consultoria", description = "Cria uma nova ordem de serviço de consultoria")
    public ResponseEntity<OrdemServicoDTO> criarConsultoria(@Valid @RequestBody OSConsultoriaDTO dto) {
        OrdemServicoDTO osCriada = ordemServicoService.criarConsultoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(osCriada);
    }
    
    @GetMapping
    @Operation(summary = "Listar todas as OS", description = "Retorna lista de todas as ordens de serviço")
    public ResponseEntity<List<OrdemServicoDTO>> listarTodas() {
        List<OrdemServicoDTO> ordensServico = ordemServicoService.listarTodas();
        return ResponseEntity.ok(ordensServico);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar OS por ID", description = "Retorna uma ordem de serviço específica pelo ID")
    public ResponseEntity<OrdemServicoDTO> buscarPorId(@PathVariable Long id) {
        OrdemServicoDTO os = ordemServicoService.buscarPorId(id);
        return ResponseEntity.ok(os);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar OS por status", description = "Retorna ordens de serviço com o status especificado")
    public ResponseEntity<List<OrdemServicoDTO>> buscarPorStatus(@PathVariable StatusOS status) {
        List<OrdemServicoDTO> ordensServico = ordemServicoService.buscarPorStatus(status);
        return ResponseEntity.ok(ordensServico);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar OS por cliente", description = "Retorna ordens de serviço de um cliente específico")
    public ResponseEntity<List<OrdemServicoDTO>> buscarPorCliente(@PathVariable Long clienteId) {
        List<OrdemServicoDTO> ordensServico = ordemServicoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(ordensServico);
    }
    
    @GetMapping("/tecnico/{tecnicoId}")
    @Operation(summary = "Buscar OS por técnico", description = "Retorna ordens de serviço de um técnico específico")
    public ResponseEntity<List<OrdemServicoDTO>> buscarPorTecnico(@PathVariable Long tecnicoId) {
        List<OrdemServicoDTO> ordensServico = ordemServicoService.buscarPorTecnico(tecnicoId);
        return ResponseEntity.ok(ordensServico);
    }
    
    @GetMapping("/antigas")
    @Operation(summary = "Buscar OS antigas", description = "Retorna ordens de serviço abertas há mais de X dias")
    public ResponseEntity<List<OrdemServicoDTO>> buscarOSAntigas(@RequestParam(defaultValue = "30") int dias) {
        List<OrdemServicoDTO> ordensServico = ordemServicoService.buscarOSAntigas(dias);
        return ResponseEntity.ok(ordensServico);
    }
    
    @PatchMapping("/{id}/concluir")
    @Operation(summary = "Concluir OS", description = "Marca uma ordem de serviço como concluída")
    public ResponseEntity<OrdemServicoDTO> concluir(@PathVariable Long id) {
        OrdemServicoDTO osConcluida = ordemServicoService.concluir(id);
        return ResponseEntity.ok(osConcluida);
    }
    
    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar OS", description = "Cancela uma ordem de serviço")
    public ResponseEntity<OrdemServicoDTO> cancelar(@PathVariable Long id) {
        OrdemServicoDTO osCancelada = ordemServicoService.cancelar(id);
        return ResponseEntity.ok(osCancelada);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da OS", description = "Atualiza o status de uma ordem de serviço")
    public ResponseEntity<OrdemServicoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusOS status) {
        OrdemServicoDTO osAtualizada = ordemServicoService.atualizarStatus(id, status);
        return ResponseEntity.ok(osAtualizada);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar OS", description = "Remove uma ordem de serviço do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ordemServicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
