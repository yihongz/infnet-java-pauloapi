package br.edu.infnet.pauloapi.infrastructure.loader;

import br.edu.infnet.pauloapi.domain.model.*;
import br.edu.infnet.pauloapi.infrastructure.repository.ClienteRepository;
import br.edu.infnet.pauloapi.infrastructure.repository.OrdemServicoRepository;
import br.edu.infnet.pauloapi.infrastructure.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando carga de dados...");
        
        carregarClientes();
        carregarTecnicos();
        carregarOrdensServico();
        
        log.info("Carga de dados concluída!");
    }
    
    private void carregarClientes() {
        if (clienteRepository.count() > 0) {
            log.info("Clientes já carregados. Pulando...");
            return;
        }
        
        log.info("Carregando clientes...");
        
        Cliente cliente1 = new Cliente(
            "João Silva",
            "(11) 98765-4321",
            "123.456.789-00",
            "Rua das Flores, 123 - São Paulo/SP",
            "joao.silva@email.com"
        );
        
        Cliente cliente2 = new Cliente(
            "Maria Santos",
            "(21) 99876-5432",
            "987.654.321-00",
            "Av. Atlântica, 456 - Rio de Janeiro/RJ",
            "maria.santos@email.com"
        );
        
        Cliente cliente3 = new Cliente(
            "Pedro Oliveira",
            "(31) 97654-3210",
            "456.789.123-00",
            "Rua da Bahia, 789 - Belo Horizonte/MG",
            "pedro.oliveira@email.com"
        );
        
        Cliente cliente4 = new Cliente(
            "Ana Costa",
            "(41) 96543-2109",
            "321.654.987-00",
            "Rua XV de Novembro, 321 - Curitiba/PR",
            "ana.costa@email.com"
        );
        
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);
        clienteRepository.save(cliente4);
        
        log.info("4 clientes carregados com sucesso!");
    }
    
    private void carregarTecnicos() {
        if (tecnicoRepository.count() > 0) {
            log.info("Técnicos já carregados. Pulando...");
            return;
        }
        
        log.info("Carregando técnicos...");
        
        Tecnico tecnico1 = new Tecnico(
            "Carlos Ferreira",
            "(11) 91234-5678",
            "TEC001",
            "Manutenção Elétrica"
        );
        
        Tecnico tecnico2 = new Tecnico(
            "Fernanda Lima",
            "(21) 92345-6789",
            "TEC002",
            "Instalação de Redes"
        );
        
        Tecnico tecnico3 = new Tecnico(
            "Roberto Alves",
            "(31) 93456-7890",
            "TEC003",
            "Consultoria em TI"
        );
        
        Tecnico tecnico4 = new Tecnico(
            "Juliana Souza",
            "(41) 94567-8901",
            "TEC004",
            "Manutenção Preventiva"
        );
        
        tecnicoRepository.save(tecnico1);
        tecnicoRepository.save(tecnico2);
        tecnicoRepository.save(tecnico3);
        tecnicoRepository.save(tecnico4);
        
        log.info("4 técnicos carregados com sucesso!");
    }
    
    private void carregarOrdensServico() {
        if (ordemServicoRepository.count() > 0) {
            log.info("Ordens de serviço já carregadas. Pulando...");
            return;
        }
        
        log.info("Carregando ordens de serviço...");
        
        List<Cliente> clientes = clienteRepository.findAll();
        List<Tecnico> tecnicos = tecnicoRepository.findAll();
        
        if (clientes.size() < 3 || tecnicos.size() < 3) {
            log.warn("Não há clientes ou técnicos suficientes para criar OS");
            return;
        }
        
        Cliente cliente1 = clientes.get(0);
        Cliente cliente2 = clientes.get(1);
        Cliente cliente3 = clientes.get(2);
        
        Tecnico tecnico1 = tecnicos.get(0);
        Tecnico tecnico2 = tecnicos.get(1);
        Tecnico tecnico3 = tecnicos.get(2);
        
        // OS de Manutenção 1
        OSManutencao osManutencao1 = new OSManutencao();
        osManutencao1.setDescricao("Manutenção preventiva em sistema elétrico");
        osManutencao1.setStatus(StatusOS.EM_ANDAMENTO);
        osManutencao1.setValor(new BigDecimal("150.00"));
        osManutencao1.setCliente(cliente1);
        osManutencao1.setTecnico(tecnico1);
        osManutencao1.setDataAbertura(LocalDateTime.now().minusDays(5));
        osManutencao1.setHorasTrabalhadas(8);
        osManutencao1.setValorHora(new BigDecimal("80.00"));
        osManutencao1.setTipoManutencao("Preventiva");
        osManutencao1.setObservacoes("Cliente solicita manutenção completa");
        
        // OS de Instalação 1
        OSInstalacao osInstalacao1 = new OSInstalacao();
        osInstalacao1.setDescricao("Instalação de rede estruturada");
        osInstalacao1.setStatus(StatusOS.ABERTA);
        osInstalacao1.setValor(new BigDecimal("500.00"));
        osInstalacao1.setCliente(cliente2);
        osInstalacao1.setTecnico(tecnico2);
        osInstalacao1.setDataAbertura(LocalDateTime.now().minusDays(2));
        osInstalacao1.setEquipamento("Cabo Cat6 e Switches");
        osInstalacao1.setQuantidade(10);
        osInstalacao1.setValorUnitario(new BigDecimal("50.00"));
        osInstalacao1.setIncluiMaterial(true);
        
        // OS de Consultoria 1
        OSConsultoria osConsultoria1 = new OSConsultoria();
        osConsultoria1.setDescricao("Consultoria em infraestrutura de TI");
        osConsultoria1.setStatus(StatusOS.ABERTA);
        osConsultoria1.setValor(new BigDecimal("1000.00"));
        osConsultoria1.setCliente(cliente3);
        osConsultoria1.setTecnico(tecnico3);
        osConsultoria1.setDataAbertura(LocalDateTime.now().minusDays(1));
        osConsultoria1.setAreaConsultoria("Infraestrutura e Segurança");
        osConsultoria1.setDiasConsultoria(5);
        osConsultoria1.setValorDiaria(new BigDecimal("400.00"));
        osConsultoria1.setEmiteRelatorio(true);
        
        // OS de Manutenção 2 (Concluída)
        OSManutencao osManutencao2 = new OSManutencao();
        osManutencao2.setDescricao("Reparo em sistema de ar condicionado");
        osManutencao2.setStatus(StatusOS.CONCLUIDA);
        osManutencao2.setValor(new BigDecimal("200.00"));
        osManutencao2.setCliente(cliente1);
        osManutencao2.setTecnico(tecnico1);
        osManutencao2.setDataAbertura(LocalDateTime.now().minusDays(10));
        osManutencao2.setDataConclusao(LocalDateTime.now().minusDays(8));
        osManutencao2.setHorasTrabalhadas(4);
        osManutencao2.setValorHora(new BigDecimal("80.00"));
        osManutencao2.setTipoManutencao("Corretiva");
        
        // OS de Instalação 2
        OSInstalacao osInstalacao2 = new OSInstalacao();
        osInstalacao2.setDescricao("Instalação de câmeras de segurança");
        osInstalacao2.setStatus(StatusOS.AGUARDANDO_PECA);
        osInstalacao2.setValor(new BigDecimal("800.00"));
        osInstalacao2.setCliente(cliente2);
        osInstalacao2.setTecnico(tecnico2);
        osInstalacao2.setDataAbertura(LocalDateTime.now().minusDays(3));
        osInstalacao2.setEquipamento("Câmeras IP Full HD");
        osInstalacao2.setQuantidade(8);
        osInstalacao2.setValorUnitario(new BigDecimal("350.00"));
        osInstalacao2.setIncluiMaterial(true);
        osInstalacao2.setObservacoes("Aguardando chegada das câmeras");
        
        ordemServicoRepository.save(osManutencao1);
        ordemServicoRepository.save(osInstalacao1);
        ordemServicoRepository.save(osConsultoria1);
        ordemServicoRepository.save(osManutencao2);
        ordemServicoRepository.save(osInstalacao2);
        
        log.info("5 ordens de serviço carregadas com sucesso!");
    }
}
