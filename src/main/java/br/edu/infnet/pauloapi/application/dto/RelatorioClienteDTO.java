package br.edu.infnet.pauloapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioClienteDTO {
    
    private ClienteDTO cliente;
    private Long totalOS;
    private Long osAbertas;
    private Long osConcluidas;
    private BigDecimal valorTotal;
    private List<OrdemServicoDTO> ordensServico;
}
