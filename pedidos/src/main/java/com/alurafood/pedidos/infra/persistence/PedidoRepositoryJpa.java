package com.alurafood.pedidos.infra.persistence;

import com.alurafood.pedidos.application.PedidoServiceRepository;
import com.alurafood.pedidos.domain.Pedido;
import com.alurafood.pedidos.infra.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryJpa implements PedidoServiceRepository {

    private final PedidoJpaRepository pedidoJpaRepository;

    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoEntity pedidoEntity = PedidoMapper.toEntity(pedido);
        PedidoEntity savedEntity = pedidoJpaRepository.save(pedidoEntity);
        return PedidoMapper.fromEntity(savedEntity);
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return pedidoJpaRepository.findById(id)
                .map(PedidoMapper::fromEntity);
    }

    @Override
    public Page<Pedido> listarTodos(Pageable paginacao) {
        Page<PedidoEntity> pedidosEntity = pedidoJpaRepository.findAll(paginacao);
        return pedidosEntity.map(PedidoMapper::fromEntity);

    }

    @Override
    public void atualizarStatus(UUID id, Pedido.Status novoStatus) {
        pedidoJpaRepository.atualizarStatus(id, novoStatus);
    }

    @Override
    public boolean existePorId(UUID id) {
        return pedidoJpaRepository.existsById(id);
    }
}