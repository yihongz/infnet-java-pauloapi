package br.edu.infnet.pauloapi.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CONSULTORIA")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OSConsultoria extends OrdemServico {
    
    @NotBlank(message = "Área de consultoria é obrigatória")
    @Column(name = "area_consultoria", length = 200)
    private String areaConsultoria;
    
    @NotNull(message = "Dias de consultoria é obrigatório")
    @Positive(message = "Dias de consultoria deve ser positivo")
    @Column(name = "dias_consultoria")
    private Integer diasConsultoria;
    
    @NotNull(message = "Valor diária é obrigatório")
    @Positive(message = "Valor diária deve ser positivo")
    @Column(name = "valor_diaria", precision = 10, scale = 2)
    private BigDecimal valorDiaria;
    
    @Column
    private Boolean emiteRelatorio = true;

    @Override
    public BigDecimal calcularValorTotal() {
        BigDecimal valorConsultoria = BigDecimal.valueOf(diasConsultoria)
                .multiply(valorDiaria);
        BigDecimal valorTotal = valorConsultoria.add(getValor());
        
        if (Boolean.TRUE.equals(emiteRelatorio)) {
            BigDecimal acrescimo = valorTotal.multiply(BigDecimal.valueOf(0.10));
            valorTotal = valorTotal.add(acrescimo);
        }
        
        return valorTotal;
    }
}
