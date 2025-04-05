package com.alurafood.pedidos.infra.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CriarPedidoDto(
    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    @Size(min = 1, message = "O pedido deve ter pelo menos um item")
    @Valid
    List<ItemPedidoDto> itens
) {}