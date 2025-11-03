package br.edu.infnet.pauloapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    
    private Long totalClientes;
    private Long totalTecnicos;
    private Long totalTecnicosAtivos;
    private Long totalOS;
    private Long osAbertas;
    private Long osEmAndamento;
    private Long osConcluidas;
    private Long osCanceladas;
    private BigDecimal valorTotalOS;
    private BigDecimal valorMedioOS;
}
