package br.edu.infnet.pauloapi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tecnicos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tecnico extends Pessoa {
    
    @NotBlank(message = "Matrícula é obrigatória")
    @Column(nullable = false, unique = true, length = 20)
    private String matricula;
    
    @NotBlank(message = "Especialidade é obrigatória")
    @Column(nullable = false, length = 100)
    private String especialidade;
    
    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL)
    private List<OrdemServico> ordensServico = new ArrayList<>();
    
    public Tecnico(String nome, String telefone, String matricula, String especialidade) {
        super(null, nome, telefone);
        this.matricula = matricula;
        this.especialidade = especialidade;
        this.ativo = true;
    }
}
