package br.edu.infnet.pauloapi.infrastructure.repository;

import br.edu.infnet.pauloapi.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCpf(String cpf);
    
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    
    Optional<Cliente> findByTelefone(String telefone);
    
    boolean existsByCpf(String cpf);
    
    Optional<Cliente> findByEmail(String email);
    
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.ordensServico os WHERE os.status = 'ABERTA'")
    List<Cliente> findClientesComOSAberta();

    @Query("SELECT c FROM Cliente c WHERE c.endereco LIKE %:cidade%")
    List<Cliente> findByEnderecoCidade(@Param("cidade") String cidade);
}
