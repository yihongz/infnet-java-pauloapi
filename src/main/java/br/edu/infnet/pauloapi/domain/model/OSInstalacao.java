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
@DiscriminatorValue("INSTALACAO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OSInstalacao extends OrdemServico {
    
    @NotBlank(message = "Equipamento é obrigatório")
    @Column(length = 200)
    private String equipamento;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    @Column
    private Integer quantidade;
    
    @NotNull(message = "Valor unitário é obrigatório")
    @Positive(message = "Valor unitário deve ser positivo")
    @Column(name = "valor_unitario", precision = 10, scale = 2)
    private BigDecimal valorUnitario;
    
    @Column
    private Boolean incluiMaterial = true;

    @Override
    public BigDecimal calcularValorTotal() {
        BigDecimal valorEquipamentos = BigDecimal.valueOf(quantidade)
                .multiply(valorUnitario);
        return valorEquipamentos.add(getValor());
    }
}
