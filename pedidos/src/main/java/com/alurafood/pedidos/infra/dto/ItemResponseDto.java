package com.alurafood.pedidos.infra.dto;

import com.alurafood.pedidos.domain.ItemDoPedido;

import java.util.UUID;

public record ItemResponseDto(
    UUID id,
    Integer quantidade,
    String descricao
) {
    public static ItemResponseDto fromEntity(ItemDoPedido item) {
        return new ItemResponseDto(
            item.getId(),
            item.getQuantidade(),
            item.getDescricao()
        );
    }
}