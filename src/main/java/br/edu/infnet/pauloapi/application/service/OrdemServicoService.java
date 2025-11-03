package br.edu.infnet.pauloapi.application.service;

import br.edu.infnet.pauloapi.application.dto.*;
import br.edu.infnet.pauloapi.domain.exception.BusinessException;
import br.edu.infnet.pauloapi.domain.exception.ResourceNotFoundException;
import br.edu.infnet.pauloapi.domain.model.*;
import br.edu.infnet.pauloapi.infrastructure.repository.ClienteRepository;
import br.edu.infnet.pauloapi.infrastructure.repository.OrdemServicoRepository;
import br.edu.infnet.pauloapi.infrastructure.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdemServicoService {
    
    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ModelMapper modelMapper;
    
    @Transactional
    public OrdemServicoDTO criarManutencao(OSManutencaoDTO dto) {
        log.info("Criando OS de Manutenção");
        
        Cliente cliente = buscarCliente(dto.getClienteId());
        Tecnico tecnico = buscarTecnico(dto.getTecnicoId());
        
        OSManutencao os = new OSManutencao();
        preencherDadosBase(os, dto, cliente, tecnico);
        os.setHorasTrabalhadas(dto.getHorasTrabalhadas());
        os.setValorHora(dto.getValorHora());
        os.setTipoManutencao(dto.getTipoManutencao());
        
        OSManutencao osSalva = ordemServicoRepository.save(os);
        log.info("OS de Manutenção criada. ID: {}", osSalva.getId());
        
        return converterParaDTO(osSalva);
    }
    
    @Transactional
    public OrdemServicoDTO criarInstalacao(OSInstalacaoDTO dto) {
        log.info("Criando OS de Instalação");
        
        Cliente cliente = buscarCliente(dto.getClienteId());
        Tecnico tecnico = buscarTecnico(dto.getTecnicoId());
        
        OSInstalacao os = new OSInstalacao();
        preencherDadosBase(os, dto, cliente, tecnico);
        os.setEquipamento(dto.getEquipamento());
        os.setQuantidade(dto.getQuantidade());
        os.setValorUnitario(dto.getValorUnitario());
        os.setIncluiMaterial(dto.getIncluiMaterial());
        
        OSInstalacao osSalva = ordemServicoRepository.save(os);
        log.info("OS de Instalação criada. ID: {}", osSalva.getId());
        
        return converterParaDTO(osSalva);
    }
    
    @Transactional
    public OrdemServicoDTO criarConsultoria(OSConsultoriaDTO dto) {
        log.info("Criando OS de Consultoria");
        
        Cliente cliente = buscarCliente(dto.getClienteId());
        Tecnico tecnico = buscarTecnico(dto.getTecnicoId());
        
        OSConsultoria os = new OSConsultoria();
        preencherDadosBase(os, dto, cliente, tecnico);
        os.setAreaConsultoria(dto.getAreaConsultoria());
        os.setDiasConsultoria(dto.getDiasConsultoria());
        os.setValorDiaria(dto.getValorDiaria());
        os.setEmiteRelatorio(dto.getEmiteRelatorio());
        
        OSConsultoria osSalva = ordemServicoRepository.save(os);
        log.info("OS de Consultoria criada. ID: {}", osSalva.getId());
        
        return converterParaDTO(osSalva);
    }
    
    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> listarTodas() {
        log.info("Listando todas as OS");
        return ordemServicoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public OrdemServicoDTO buscarPorId(Long id) {
        log.info("Buscando OS por ID: {}", id);
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de Serviço", "id", id));
        return converterParaDTO(os);
    }
    
    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> buscarPorStatus(StatusOS status) {
        log.info("Buscando OS por status: {}", status);
        return ordemServicoRepository.findByStatus(status).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> buscarPorCliente(Long clienteId) {
        log.info("Buscando OS por cliente ID: {}", clienteId);
        return ordemServicoRepository.findByClienteId(clienteId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> buscarPorTecnico(Long tecnicoId) {
        log.info("Buscando OS por técnico ID: {}", tecnicoId);
        return ordemServicoRepository.findByTecnicoId(tecnicoId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrdemServicoDTO concluir(Long id) {
        log.info("Concluindo OS ID: {}", id);
        
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de Serviço", "id", id));
        
        if (os.getStatus() == StatusOS.CONCLUIDA) {
            throw new BusinessException("Ordem de Serviço já está concluída");
        }
        
        if (os.getStatus() == StatusOS.CANCELADA) {
            throw new BusinessException("Não é possível concluir uma Ordem de Serviço cancelada");
        }
        
        os.concluir();
        OrdemServico osAtualizada = ordemServicoRepository.save(os);
        
        log.info("OS concluída com sucesso. ID: {}", id);
        return converterParaDTO(osAtualizada);
    }
    
    @Transactional
    public OrdemServicoDTO cancelar(Long id) {
        log.info("Cancelando OS ID: {}", id);
        
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de Serviço", "id", id));
        
        if (os.getStatus() == StatusOS.CONCLUIDA) {
            throw new BusinessException("Não é possível cancelar uma Ordem de Serviço concluída");
        }
        
        if (os.getStatus() == StatusOS.CANCELADA) {
            throw new BusinessException("Ordem de Serviço já está cancelada");
        }
        
        os.cancelar();
        OrdemServico osAtualizada = ordemServicoRepository.save(os);
        
        log.info("OS cancelada com sucesso. ID: {}", id);
        return converterParaDTO(osAtualizada);
    }
    
    @Transactional
    public OrdemServicoDTO atualizarStatus(Long id, StatusOS novoStatus) {
        log.info("Atualizando status da OS ID: {} para {}", id, novoStatus);
        
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de Serviço", "id", id));
        
        os.setStatus(novoStatus);
        OrdemServico osAtualizada = ordemServicoRepository.save(os);
        
        log.info("Status da OS atualizado com sucesso. ID: {}", id);
        return converterParaDTO(osAtualizada);
    }
    
    @Transactional
    public void deletar(Long id) {
        log.info("Deletando OS ID: {}", id);
        
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de Serviço", "id", id));
        
        if (os.getStatus() == StatusOS.EM_ANDAMENTO) {
            throw new BusinessException("Não é possível deletar uma OS em andamento");
        }
        
        ordemServicoRepository.delete(os);
        log.info("OS deletada com sucesso. ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> buscarOSAntigas(int dias) {
        log.info("Buscando OS abertas há mais de {} dias", dias);
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(dias);
        return ordemServicoRepository.findOSAbertasAntigas(dataLimite).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    private Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));
    }
    
    private Tecnico buscarTecnico(Long tecnicoId) {
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico", "id", tecnicoId));
        
        if (!tecnico.getAtivo()) {
            throw new BusinessException("Técnico está inativo");
        }
        
        return tecnico;
    }
    
    private void preencherDadosBase(OrdemServico os, OrdemServicoDTO dto, Cliente cliente, Tecnico tecnico) {
        os.setDescricao(dto.getDescricao());
        os.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusOS.ABERTA);
        os.setValor(dto.getValor());
        os.setObservacoes(dto.getObservacoes());
        os.setCliente(cliente);
        os.setTecnico(tecnico);
        os.setDataAbertura(LocalDateTime.now());
    }
    
    private OrdemServicoDTO converterParaDTO(OrdemServico os) {
        OrdemServicoDTO dto;
        
        if (os instanceof OSManutencao) {
            dto = modelMapper.map(os, OSManutencaoDTO.class);
            dto.setTipoOS("MANUTENCAO");
        } else if (os instanceof OSInstalacao) {
            dto = modelMapper.map(os, OSInstalacaoDTO.class);
            dto.setTipoOS("INSTALACAO");
        } else if (os instanceof OSConsultoria) {
            dto = modelMapper.map(os, OSConsultoriaDTO.class);
            dto.setTipoOS("CONSULTORIA");
        } else {
            dto = modelMapper.map(os, OrdemServicoDTO.class);
        }
        
        dto.setClienteId(os.getCliente().getId());
        dto.setClienteNome(os.getCliente().getNome());
        dto.setTecnicoId(os.getTecnico().getId());
        dto.setTecnicoNome(os.getTecnico().getNome());
        
        return dto;
    }
}
