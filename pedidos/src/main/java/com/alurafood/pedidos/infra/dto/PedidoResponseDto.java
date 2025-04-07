package com.alurafood.pedidos.infra.dto;

import com.alurafood.pedidos.domain.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDto(
    UUID id,
    LocalDateTime dataHora,
    Pedido.Status status,
    List<ItemPedidoDto> itens,
    int quantidadeItens,
    BigDecimal total,
    String mensagem
) {
    // Construtor compacto para garantir que a lista de itens nunca seja nula
    public PedidoResponseDto {
        if (itens == null) {
            itens = new ArrayList<>();
        }
    }
}