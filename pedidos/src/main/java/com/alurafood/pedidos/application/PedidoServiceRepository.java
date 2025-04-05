package com.alurafood.pedidos.application;

import com.alurafood.pedidos.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface PedidoServiceRepository {
    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(UUID id);
    Page<Pedido> listarTodos(Pageable paginacao);
    void atualizarStatus(UUID id, Pedido.Status novoStatus);
    boolean existePorId(UUID id);
}