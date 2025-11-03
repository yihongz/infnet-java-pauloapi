package br.edu.infnet.pauloapi.presentation.controllers;

import br.edu.infnet.pauloapi.application.dto.DashboardDTO;
import br.edu.infnet.pauloapi.application.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "API para estatísticas e dashboard")
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @GetMapping("/estatisticas")
    @Operation(summary = "Obter estatísticas", description = "Retorna estatísticas gerais do sistema")
    public ResponseEntity<DashboardDTO> obterEstatisticas() {
        DashboardDTO dashboard = dashboardService.obterEstatisticas();
        return ResponseEntity.ok(dashboard);
    }
}
