package com.alurafood.pagamentos.infra.dto;

import com.alurafood.pagamentos.domain.Pagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoDto(

        UUID id,

        @NotNull(message = "O valor não pode ser nulo")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal valor,

        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @Size(max = 19, message = "O número deve ter no máximo 19 caracteres")
        String numero,

        @Size(max = 7, message = "A data de expiração deve ter no máximo 7 caracteres")
        String expiracao,

        @Size(min = 3, max = 3, message = "O código de segurança deve ter exatamente 3 caracteres")
        String codigo,

        Pagamento.Status status,

        @NotNull(message = "O ID da forma de pagamento não pode ser nulo")
        UUID formaDePagamentoId,

        @NotNull(message = "O ID do pedido não pode ser nulo")
        UUID pedidoId

) {}
