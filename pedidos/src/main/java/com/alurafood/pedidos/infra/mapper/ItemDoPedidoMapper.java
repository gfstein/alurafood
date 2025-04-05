package com.alurafood.pedidos.infra.mapper;

import com.alurafood.pedidos.domain.ItemDoPedido;
import com.alurafood.pedidos.infra.persistence.ItemDoPedidoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por converter entre os diferentes modelos de ItemDoPedido:
 * - Domínio (ItemDoPedido)
 * - Entidade (ItemDoPedidoEntity)
 */
public class ItemDoPedidoMapper {

    private ItemDoPedidoMapper() {
        // Construtor privado para evitar instanciação
    }

    /**
     * Converte uma entidade de persistência para um objeto de domínio
     */
    public static ItemDoPedido fromEntity(ItemDoPedidoEntity entity) {
        if (entity == null) {
            return null;
        }

        return ItemDoPedido.create(
                entity.getId(),
                entity.getQuantidade(),
                entity.getDescricao()
                );
    }

    /**
     * Converte uma lista de entidades para uma lista de objetos de domínio
     */
    public static List<ItemDoPedido> fromEntityList(List<ItemDoPedidoEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(ItemDoPedidoMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converte um objeto de domínio para uma entidade de persistência
     */
    public static ItemDoPedidoEntity toEntity(ItemDoPedido domain) {
        if (domain == null) {
            return null;
        }

        ItemDoPedidoEntity entity = new ItemDoPedidoEntity();
        entity.setId(domain.getId());
        entity.setDescricao(domain.getDescricao());
        entity.setQuantidade(domain.getQuantidade());
        // O pedido associado será definido pelo PedidoMapper

        return entity;
    }

    /**
     * Converte uma lista de objetos de domínio para uma lista de entidades
     * Obs: Neste caso, o pedido associado deverá ser definido posteriormente
     */
    public static List<ItemDoPedidoEntity> toEntityList(List<ItemDoPedido> domains) {
        if (domains == null) {
            return new ArrayList<>();
        }

        return domains.stream()
                .map(ItemDoPedidoMapper::toEntity)
                .collect(Collectors.toList());
    }

}