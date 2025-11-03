package br.edu.infnet.pauloapi.infrastructure.repository;

import br.edu.infnet.pauloapi.domain.model.OrdemServico;
import br.edu.infnet.pauloapi.domain.model.StatusOS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    List<OrdemServico> findByStatus(StatusOS status);
    
    List<OrdemServico> findByClienteId(Long clienteId);
    
    List<OrdemServico> findByTecnicoId(Long tecnicoId);
    
    List<OrdemServico> findByDataAberturaBetween(LocalDateTime inicio, LocalDateTime fim);
    
    List<OrdemServico> findByValorGreaterThan(BigDecimal valor);

    List<OrdemServico> findByClienteIdAndStatus(Long clienteId, StatusOS status);

    List<OrdemServico> findByTecnicoIdAndStatus(Long tecnicoId, StatusOS status);

    @Query("SELECT os FROM OrdemServico os WHERE os.status = 'ABERTA' AND os.dataAbertura < :dataLimite")
    List<OrdemServico> findOSAbertasAntigas(@Param("dataLimite") LocalDateTime dataLimite);
    
    @Query("SELECT os FROM OrdemServico os WHERE TYPE(os) = :tipo")
    List<OrdemServico> findByTipo(@Param("tipo") Class<? extends OrdemServico> tipo);

    @Query("SELECT SUM(os.valor) FROM OrdemServico os WHERE os.cliente.id = :clienteId")
    BigDecimal calcularValorTotalPorCliente(@Param("clienteId") Long clienteId);
    
    @Query("SELECT os.status, COUNT(os) FROM OrdemServico os GROUP BY os.status")
    List<Object[]> countOSPorStatus();
    
    @Query("SELECT os FROM OrdemServico os WHERE LOWER(os.descricao) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<OrdemServico> findByDescricaoContaining(@Param("texto") String texto);
    
    List<OrdemServico> findByStatusAndDataConclusaoBetween(StatusOS status, LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT os FROM OrdemServico os WHERE os.cliente.id = :clienteId AND os.dataAbertura BETWEEN :inicio AND :fim")
    List<OrdemServico> findByClienteAndPeriodo(@Param("clienteId") Long clienteId, 
                                                @Param("inicio") LocalDateTime inicio, 
                                                @Param("fim") LocalDateTime fim);
}
