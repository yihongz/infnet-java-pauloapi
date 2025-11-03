package br.edu.infnet.pauloapi.application.service;

import br.edu.infnet.pauloapi.application.dto.ClienteDTO;
import br.edu.infnet.pauloapi.domain.exception.BusinessException;
import br.edu.infnet.pauloapi.domain.exception.ResourceNotFoundException;
import br.edu.infnet.pauloapi.domain.model.Cliente;
import br.edu.infnet.pauloapi.infrastructure.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    
    @Transactional
    public ClienteDTO criar(ClienteDTO clienteDTO) {
        log.info("Criando novo cliente: {}", clienteDTO.getNome());
        
        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + clienteDTO.getCpf());
        }
        
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        
        log.info("Cliente criado com sucesso. ID: {}", clienteSalvo.getId());
        return modelMapper.map(clienteSalvo, ClienteDTO.class);
    }
    
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarTodos() {
        log.info("Listando todos os clientes");
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorCpf(String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "CPF", cpf));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarPorNome(String nome) {
        log.info("Buscando clientes por nome: {}", nome);
        return clienteRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        log.info("Atualizando cliente ID: {}", id);
        
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        
        if (!clienteExistente.getCpf().equals(clienteDTO.getCpf())) {
            if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
                throw new BusinessException("CPF já cadastrado: " + clienteDTO.getCpf());
            }
        }
        
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setCpf(clienteDTO.getCpf());
        clienteExistente.setTelefone(clienteDTO.getTelefone());
        clienteExistente.setEndereco(clienteDTO.getEndereco());
        clienteExistente.setEmail(clienteDTO.getEmail());
        
        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        log.info("Cliente atualizado com sucesso. ID: {}", clienteAtualizado.getId());
        
        return modelMapper.map(clienteAtualizado, ClienteDTO.class);
    }
    
    @Transactional
    public void deletar(Long id) {
        log.info("Deletando cliente ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        
        if (!cliente.getOrdensServico().isEmpty()) {
            throw new BusinessException("Não é possível deletar cliente com ordens de serviço associadas");
        }
        
        clienteRepository.delete(cliente);
        log.info("Cliente deletado com sucesso. ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarClientesComOSAberta() {
        log.info("Buscando clientes com OS em aberto");
        return clienteRepository.findClientesComOSAberta().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
}
