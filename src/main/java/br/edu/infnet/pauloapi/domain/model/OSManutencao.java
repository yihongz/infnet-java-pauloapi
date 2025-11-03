package br.edu.infnet.pauloapi.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("MANUTENCAO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OSManutencao extends OrdemServico {
    
    @NotNull(message = "Horas trabalhadas é obrigatório")
    @Positive(message = "Horas trabalhadas deve ser positivo")
    @Column(name = "horas_trabalhadas")
    private Integer horasTrabalhadas;
    
    @NotNull(message = "Valor hora é obrigatório")
    @Positive(message = "Valor hora deve ser positivo")
    @Column(name = "valor_hora", precision = 10, scale = 2)
    private BigDecimal valorHora;
    
    @Column(length = 200)
    private String tipoManutencao; // Preventiva, Corretiva, Preditiva
    
    @Override
    public BigDecimal calcularValorTotal() {
        BigDecimal valorServico = BigDecimal.valueOf(horasTrabalhadas)
                .multiply(valorHora);
        return valorServico.add(getValor());
    }
}
