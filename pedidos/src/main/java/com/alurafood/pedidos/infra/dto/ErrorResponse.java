package com.alurafood.pedidos.infra.dto;

public record ErrorResponse(
        Integer status,
        String error,
        String message) {
}
