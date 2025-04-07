package com.alurafood.pagamentos.application.port;

import java.util.UUID;

public interface PedidoNotificacao {
    void notificarPagamentoConcluido(UUID pedidoId);
}
