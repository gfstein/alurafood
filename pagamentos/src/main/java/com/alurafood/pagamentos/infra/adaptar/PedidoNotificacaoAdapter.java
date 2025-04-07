package com.alurafood.pagamentos.infra.adaptar;

import com.alurafood.pagamentos.application.port.PedidoNotificacao;
import com.alurafood.pagamentos.infra.http.PedidoClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class PedidoNotificacaoAdapter implements PedidoNotificacao {

    private final PedidoClient pedidoClient;

    @Override
    public void notificarPagamentoConcluido(UUID pedidoId) {
        pedidoClient.atualizaPagamento(pedidoId);
    }
}
