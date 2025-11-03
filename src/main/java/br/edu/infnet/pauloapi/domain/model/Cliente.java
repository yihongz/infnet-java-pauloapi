package br.edu.infnet.pauloapi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Pessoa {
    
    @NotBlank(message = "CPF é obrigatório")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false, length = 200)
    private String endereco;
    
    @Email(message = "Email deve ser válido")
    @Column(length = 100)
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemServico> ordensServico = new ArrayList<>();
    
    public Cliente(String nome, String telefone, String cpf, String endereco, String email) {
        super(null, nome, telefone);
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
    }
}
