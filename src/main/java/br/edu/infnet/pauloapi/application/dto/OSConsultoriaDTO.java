package br.edu.infnet.pauloapi.application.dto;

import jakarta.validation.constraints.NotBlank;
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
public class OSConsultoriaDTO extends OrdemServicoDTO {
    
    @NotBlank(message = "Área de consultoria é obrigatória")
    private String areaConsultoria;
    
    @NotNull(message = "Dias de consultoria é obrigatório")
    @Positive(message = "Dias de consultoria deve ser positivo")
    private Integer diasConsultoria;
    
    @NotNull(message = "Valor diária é obrigatório")
    @Positive(message = "Valor diária deve ser positivo")
    private BigDecimal valorDiaria;
    
    private Boolean emiteRelatorio;
}
