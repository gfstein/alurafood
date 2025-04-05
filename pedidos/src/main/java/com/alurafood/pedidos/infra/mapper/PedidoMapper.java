package com.alurafood.pedidos.infra.mapper;

import com.alurafood.pedidos.domain.Pedido;
import com.alurafood.pedidos.infra.persistence.PedidoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por converter entre os diferentes modelos de Pedido:
 * - Domínio (Pedido)
 * - Entidade (PedidoEntity)
 */
public class PedidoMapper {

    private PedidoMapper() {
        // Construtor privado para evitar instanciação
    }

    /**
     * Converte uma entidade de persistência para um objeto de domínio
     */
    public static Pedido fromEntity(PedidoEntity entity) {
        if (entity == null) {
            return null;
        }

        var itensDomain = entity.getItens().stream()
                .map(ItemDoPedidoMapper::fromEntity)
                .collect(Collectors.toList());

        return Pedido.create(
                entity.getId(),
                entity.getDataHora(),
                entity.getStatus(),
                itensDomain
        );
    }

    /**
     * Converte uma lista de entidades para uma lista de objetos de domínio
     */
    public static List<Pedido> fromEntityList(List<PedidoEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(PedidoMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converte um objeto de domínio para uma entidade de persistência
     */
    public static PedidoEntity toEntity(Pedido domain) {
        if (domain == null) {
            return null;
        }

        PedidoEntity entity = new PedidoEntity();
        entity.setId(domain.getId());
        entity.setDataHora(domain.getDataHora());
        entity.setStatus(domain.getStatus());

        // A lista de itens é inicializada vazia
        entity.setItens(new ArrayList<>());

        // Se o pedido possui itens, adiciona-os à entidade
        if (domain.getItens() != null) {
            domain.getItens().forEach(item -> {
                var itemEntity = ItemDoPedidoMapper.toEntity(item);
                itemEntity.setPedido(entity);
                entity.getItens().add(itemEntity);
            });
        }

        return entity;
    }

    /**
     * Converte uma lista de objetos de domínio para uma lista de entidades
     */
    public static List<PedidoEntity> toEntityList(List<Pedido> domains) {
        if (domains == null) {
            return new ArrayList<>();
        }

        return domains.stream()
                .map(PedidoMapper::toEntity)
                .collect(Collectors.toList());
    }

}