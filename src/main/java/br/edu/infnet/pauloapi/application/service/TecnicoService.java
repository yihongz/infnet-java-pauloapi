package br.edu.infnet.pauloapi.application.service;

import br.edu.infnet.pauloapi.application.dto.TecnicoDTO;
import br.edu.infnet.pauloapi.domain.exception.BusinessException;
import br.edu.infnet.pauloapi.domain.exception.ResourceNotFoundException;
import br.edu.infnet.pauloapi.domain.model.Tecnico;
import br.edu.infnet.pauloapi.infrastructure.repository.TecnicoRepository;
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
public class TecnicoService {
    
    private final TecnicoRepository tecnicoRepository;
    private final ModelMapper modelMapper;
    
    @Transactional
    public TecnicoDTO criar(TecnicoDTO tecnicoDTO) {
        log.info("Criando novo técnico: {}", tecnicoDTO.getNome());
        
        if (tecnicoRepository.existsByMatricula(tecnicoDTO.getMatricula())) {
            throw new BusinessException("Matrícula já cadastrada: " + tecnicoDTO.getMatricula());
        }
        
        Tecnico tecnico = modelMapper.map(tecnicoDTO, Tecnico.class);
        if (tecnico.getAtivo() == null) {
            tecnico.setAtivo(true);
        }
        
        Tecnico tecnicoSalvo = tecnicoRepository.save(tecnico);
        log.info("Técnico criado com sucesso. ID: {}", tecnicoSalvo.getId());
        
        return modelMapper.map(tecnicoSalvo, TecnicoDTO.class);
    }
    
    @Transactional(readOnly = true)
    public List<TecnicoDTO> listarTodos() {
        log.info("Listando todos os técnicos");
        return tecnicoRepository.findAll().stream()
                .map(tecnico -> modelMapper.map(tecnico, TecnicoDTO.class))
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public TecnicoDTO buscarPorId(Long id) {
        log.info("Buscando técnico por ID: {}", id);
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico", "id", id));
        return modelMapper.map(tecnico, TecnicoDTO.class);
    }
    
    @Transactional(readOnly = true)
    public TecnicoDTO buscarPorMatricula(String matricula) {
        log.info("Buscando técnico por matrícula: {}", matricula);
        Tecnico tecnico = tecnicoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico", "matrícula", matricula));
        return modelMapper.map(tecnico, TecnicoDTO.class);
    }
    
    @Transactional(readOnly = true)
    public List<TecnicoDTO> buscarPorEspecialidade(String especialidade) {
        log.info("Buscando técnicos por especialidade: {}", especialidade);
        return tecnicoRepository.findByEspecialidadeContainingIgnoreCase(especialidade).stream()
                .map(tecnico -> modelMapper.map(tecnico, TecnicoDTO.class))
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TecnicoDTO> listarAtivos() {
        log.info("Listando técnicos ativos");
        return tecnicoRepository.findByAtivoTrue().stream()
                .map(tecnico -> modelMapper.map(tecnico, TecnicoDTO.class))
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TecnicoDTO> listarDisponiveis() {
        log.info("Listando técnicos disponíveis");
        return tecnicoRepository.findTecnicosDisponiveis().stream()
                .map(tecnico -> modelMapper.map(tecnico, TecnicoDTO.class))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public TecnicoDTO atualizar(Long id, TecnicoDTO tecnicoDTO) {
        log.info("Atualizando técnico ID: {}", id);
        
        Tecnico tecnicoExistente = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico", "id", id));
        
        if (!tecnicoExistente.getMatricula().equals(tecnicoDTO.getMatricula())) {
            if (tecnicoRepository.existsByMatricula(tecnicoDTO.getMatricula())) {
                throw new BusinessException("Matrícula já cadastrada: " + tecnicoDTO.getMatricula());
            }
        }
        
        tecnicoExistente.setNome(tecnicoDTO.getNome());
        tecnicoExistente.setMatricula(tecnicoDTO.getMatricula());
        tecnicoExistente.setTelefone(tecnicoDTO.getTelefone());
        tecnicoExistente.setEspecialidade(tecnicoDTO.getEspecialidade());
        if (tecnicoDTO.getAtivo() != null) {
            tecnicoExistente.setAtivo(tecnicoDTO.getAtivo());
        }
        
        Tecnico tecnicoAtualizado = tecnicoRepository.save(tecnicoExistente);
        log.info("Técnico atualizado com sucesso. ID: {}", tecnicoAtualizado.getId());
        
        return modelMapper.map(tecnicoAtualizado, TecnicoDTO.class);
    }
    
    @Transactional
    public void deletar(Long id) {
        log.info("Deletando técnico ID: {}", id);
        
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico", "id", id));
        
        if (!tecnico.getOrdensServico().isEmpty()) {
            throw new BusinessException("Não é possível deletar técnico com ordens de serviço associadas");
        }
        
        tecnicoRepository.delete(tecnico);
        log.info("Técnico deletado com sucesso. ID: {}", id);
    }
    
    @Transactional
    public TecnicoDTO inativar(Long id) {
        log.info("Inativando técnico ID: {}", id);
        
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico", "id", id));
        
        tecnico.setAtivo(false);
        Tecnico tecnicoAtualizado = tecnicoRepository.save(tecnico);
        
        log.info("Técnico inativado com sucesso. ID: {}", id);
        return modelMapper.map(tecnicoAtualizado, TecnicoDTO.class);
    }
}
