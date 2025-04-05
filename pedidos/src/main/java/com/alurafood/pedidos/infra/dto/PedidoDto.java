package com.alurafood.pedidos.infra.dto;

import com.alurafood.pedidos.domain.Pedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record PedidoDto(
    UUID id,
    
    LocalDateTime dataHora,
    
    Pedido.Status status,
    
    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    @Valid
    List<ItemPedidoDto> itens
) {
    // Construtor compacto para garantir que a lista de itens nunca seja nula
    public PedidoDto {
        if (itens == null) {
            itens = new ArrayList<>();
        }
    }
}