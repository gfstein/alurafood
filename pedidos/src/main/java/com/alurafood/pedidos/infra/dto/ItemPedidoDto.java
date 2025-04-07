package com.alurafood.pedidos.infra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoDto(
    UUID id,
    
    @NotBlank(message = "A descrição do item é obrigatória")
    String descricao,
    
    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    Integer quantidade,

    @NotNull(message = "O valor é obrigatória")
    @Positive(message = "O valor deve ser maior que zero")
    BigDecimal valor
) {
}