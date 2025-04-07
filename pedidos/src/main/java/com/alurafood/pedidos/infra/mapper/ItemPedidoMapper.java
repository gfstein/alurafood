package com.alurafood.pedidos.infra.mapper;

import com.alurafood.pedidos.domain.ItemPedido;
import com.alurafood.pedidos.infra.persistence.ItemPedidoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por converter entre os diferentes modelos de ItemDoPedido:
 * - Domínio (ItemDoPedido)
 * - Entidade (ItemDoPedidoEntity)
 */
public class ItemPedidoMapper {

    private ItemPedidoMapper() {
        // Construtor privado para evitar instanciação
    }

    /**
     * Converte uma entidade de persistência para um objeto de domínio
     */
    public static ItemPedido fromEntity(ItemPedidoEntity entity) {
        if (entity == null) {
            return null;
        }

        return ItemPedido.create(
                entity.getId(),
                entity.getQuantidade(),
                entity.getDescricao(),
                entity.getValor()
                );
    }

    /**
     * Converte uma lista de entidades para uma lista de objetos de domínio
     */
    public static List<ItemPedido> fromEntityList(List<ItemPedidoEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(ItemPedidoMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converte um objeto de domínio para uma entidade de persistência
     */
    public static ItemPedidoEntity toEntity(ItemPedido domain) {
        if (domain == null) {
            return null;
        }

        ItemPedidoEntity entity = new ItemPedidoEntity();
        entity.setId(domain.getId());
        entity.setDescricao(domain.getDescricao());
        entity.setQuantidade(domain.getQuantidade());
        entity.setValor(domain.getValor());
        // O pedido associado será definido pelo PedidoMapper

        return entity;
    }

    /**
     * Converte uma lista de objetos de domínio para uma lista de entidades
     * Obs: Neste caso, o pedido associado deverá ser definido posteriormente
     */
    public static List<ItemPedidoEntity> toEntityList(List<ItemPedido> domains) {
        if (domains == null) {
            return new ArrayList<>();
        }

        return domains.stream()
                .map(ItemPedidoMapper::toEntity)
                .collect(Collectors.toList());
    }

}