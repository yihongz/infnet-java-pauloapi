package br.edu.infnet.pauloapi.infrastructure.repository;

import br.edu.infnet.pauloapi.domain.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    
    Optional<Tecnico> findByMatricula(String matricula);
    
    List<Tecnico> findByEspecialidadeContainingIgnoreCase(String especialidade);
    
    List<Tecnico> findByAtivoTrue();
    
    List<Tecnico> findByNomeContainingIgnoreCase(String nome);
    
    boolean existsByMatricula(String matricula);

    @Query("SELECT t FROM Tecnico t WHERE t.ativo = true AND " +
           "t.id NOT IN (SELECT os.tecnico.id FROM OrdemServico os WHERE os.status IN ('ABERTA', 'EM_ANDAMENTO'))")
    List<Tecnico> findTecnicosDisponiveis();
    
    @Query("SELECT t FROM Tecnico t WHERE t.especialidade = :especialidade AND t.ativo = true")
    List<Tecnico> findByEspecialidadeAndAtivo(@Param("especialidade") String especialidade);
    
    @Query("SELECT t.id, COUNT(os) FROM Tecnico t LEFT JOIN t.ordensServico os GROUP BY t.id")
    List<Object[]> countOSPorTecnico();
}
