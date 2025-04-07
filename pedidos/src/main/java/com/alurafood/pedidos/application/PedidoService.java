package com.alurafood.pedidos.application;

import com.alurafood.pedidos.domain.ItemPedido;
import com.alurafood.pedidos.domain.Pedido;
import com.alurafood.pedidos.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoServiceRepository pedidoRepository;

    @Transactional
    public Pedido criarPedido(List<ItemPedido> itens) {
        Pedido pedido = Pedido.novoPedido(itens);
        return pedidoRepository.salvar(pedido);
    }

    @Transactional(readOnly = true)
    public Page<Pedido> listarTodos(Pageable paginacao) {
        return pedidoRepository.listarTodos(paginacao);
    }


    @Transactional(readOnly = true)
    public Pedido buscarPedidoPorId(UUID id) {
        return pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new DomainException("Pedido não encontrado com o ID: " + id));
    }

    @Transactional
    public void atualizarStatusPedido(UUID id, Pedido.Status novoStatus) {
        if (!pedidoRepository.existePorId(id)) {
            throw new DomainException("Pedido não encontrado com o ID: " + id);
        }

        pedidoRepository.atualizarStatus(id, novoStatus);
    }

    @Transactional
    public Pedido adicionarItemAoPedido(UUID pedidoId, ItemPedido novoItem) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new DomainException("Pedido não encontrado com o ID: " + pedidoId));

        pedido.adicionarItem(novoItem);

        return pedidoRepository.salvar(pedido);
    }

    @Transactional
    public Pedido removerItemDoPedido(UUID pedidoId, UUID itemId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new DomainException("Pedido não encontrado com o ID: " + pedidoId));

        pedido.removerItem(itemId);

        return pedidoRepository.salvar(pedido);
    }

    @Transactional
    public Pedido aprovaPagamentoPedido(UUID pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new DomainException("Pedido não encontrado com o ID: " + pedidoId));

        // A lógica de negócio foi movida para o modelo de domínio
        pedido.aprovarPagamento();

        return pedidoRepository.salvar(pedido);
    }

}