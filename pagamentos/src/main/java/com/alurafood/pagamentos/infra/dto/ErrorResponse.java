package com.alurafood.pagamentos.infra.dto;

public record ErrorResponse(
        Integer status,
        String error,
        String message) {
}
