package br.edu.infnet.pauloapi.application.service;

import br.edu.infnet.pauloapi.application.dto.DashboardDTO;
import br.edu.infnet.pauloapi.domain.model.StatusOS;
import br.edu.infnet.pauloapi.infrastructure.repository.ClienteRepository;
import br.edu.infnet.pauloapi.infrastructure.repository.OrdemServicoRepository;
import br.edu.infnet.pauloapi.infrastructure.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {
    
    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    
    @Transactional(readOnly = true)
    public DashboardDTO obterEstatisticas() {
        log.info("Obtendo estatísticas do dashboard");
        
        DashboardDTO dashboard = new DashboardDTO();
        
        // Contadores
        dashboard.setTotalClientes(clienteRepository.count());
        dashboard.setTotalTecnicos(tecnicoRepository.count());
        dashboard.setTotalTecnicosAtivos((long) tecnicoRepository.findByAtivoTrue().size());
        dashboard.setTotalOS(ordemServicoRepository.count());
        
        // OS por status
        dashboard.setOsAbertas((long) ordemServicoRepository.findByStatus(StatusOS.ABERTA).size());
        dashboard.setOsEmAndamento((long) ordemServicoRepository.findByStatus(StatusOS.EM_ANDAMENTO).size());
        dashboard.setOsConcluidas((long) ordemServicoRepository.findByStatus(StatusOS.CONCLUIDA).size());
        dashboard.setOsCanceladas((long) ordemServicoRepository.findByStatus(StatusOS.CANCELADA).size());
        
        // Valores
        BigDecimal valorTotal = ordemServicoRepository.findAll().stream()
                .map(os -> os.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        dashboard.setValorTotalOS(valorTotal);
        
        if (dashboard.getTotalOS() > 0) {
            BigDecimal valorMedio = valorTotal.divide(
                    BigDecimal.valueOf(dashboard.getTotalOS()), 
                    2, 
                    RoundingMode.HALF_UP
            );
            dashboard.setValorMedioOS(valorMedio);
        } else {
            dashboard.setValorMedioOS(BigDecimal.ZERO);
        }
        
        log.info("Estatísticas obtidas com sucesso");
        return dashboard;
    }
}
