package br.edu.infnet.pauloapi.presentation.controllers;

import br.edu.infnet.pauloapi.application.dto.OrdemServicoDTO;
import br.edu.infnet.pauloapi.application.service.OrdemServicoService;
import br.edu.infnet.pauloapi.domain.model.StatusOS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "API para geração de relatórios")
public class RelatorioController {
    
    private final OrdemServicoService ordemServicoService;
    
    @GetMapping("/os-periodo")
    @Operation(summary = "Relatório de OS por período", 
               description = "Retorna ordens de serviço em um período específico")
    public ResponseEntity<List<OrdemServicoDTO>> relatorioOSPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        
        List<OrdemServicoDTO> ordensServico = ordemServicoService.listarTodas().stream()
                .filter(os -> os.getDataAbertura().isAfter(inicio) && os.getDataAbertura().isBefore(fim))
                .toList();
        
        return ResponseEntity.ok(ordensServico);
    }
    
    @GetMapping("/os-por-status")
    @Operation(summary = "Relatório de OS por status", 
               description = "Retorna quantidade de OS agrupadas por status")
    public ResponseEntity<List<OrdemServicoDTO>> relatorioOSPorStatus(
            @RequestParam StatusOS status) {
        
        List<OrdemServicoDTO> ordensServico = ordemServicoService.buscarPorStatus(status);
        return ResponseEntity.ok(ordensServico);
    }
}
