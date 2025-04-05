package com.alurafood.pedidos.infra.dto;

import com.alurafood.pedidos.domain.Pedido;
import jakarta.validation.constraints.NotNull;

public record AtualizaStatusDto(
    @NotNull(message = "O status é obrigatório")
    Pedido.Status status
) {
}