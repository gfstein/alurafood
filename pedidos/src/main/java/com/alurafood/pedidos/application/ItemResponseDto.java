package com.alurafood.pedidos.application;

import com.alurafood.pedidos.domain.ItemPedido;

import java.util.UUID;

public record ItemResponseDto(
    UUID id,
    Integer quantidade,
    String descricao
) {
    public static ItemResponseDto fromEntity(ItemPedido item) {
        return new ItemResponseDto(
            item.getId(),
            item.getQuantidade(),
            item.getDescricao()
        );
    }
}