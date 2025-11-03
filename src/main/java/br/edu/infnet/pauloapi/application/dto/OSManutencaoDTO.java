package br.edu.infnet.pauloapi.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OSManutencaoDTO extends OrdemServicoDTO {
    
    @NotNull(message = "Horas trabalhadas é obrigatório")
    @Positive(message = "Horas trabalhadas deve ser positivo")
    private Integer horasTrabalhadas;
    
    @NotNull(message = "Valor hora é obrigatório")
    @Positive(message = "Valor hora deve ser positivo")
    private BigDecimal valorHora;
    
    private String tipoManutencao;
}
