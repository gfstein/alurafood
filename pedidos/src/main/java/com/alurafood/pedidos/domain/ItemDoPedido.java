package com.alurafood.pedidos.domain;

import com.alurafood.pedidos.exceptions.DomainException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ItemDoPedido {

    private UUID id;
    private Integer quantidade;
    private String descricao;
    
    private ItemDoPedido() {}

    public static ItemDoPedido create(UUID id, Integer quantidade, String descricao) {
        ItemDoPedido item = new ItemDoPedido();
        item.setId(id);
        item.setQuantidade(quantidade);
        item.setDescricao(descricao);
        return item;
    }

    public static ItemDoPedido novoItemDePedido(Integer quantidade, String descricao) {
        return ItemDoPedido.create(UUID.randomUUID(), quantidade, descricao);
    }
    
    
    private void setId(UUID id) {

        if (id == null) {
            throw new DomainException("Id não pode ser nulo");
        }

        this.id = id;
    }

    private void setQuantidade(Integer quantidade) {

        if (quantidade == null || quantidade < 1) {
            throw new DomainException("Quantidade não pode ser nula ou menor que 1");
        }

        this.quantidade = quantidade;
    }

    private void setDescricao(String descricao) {

        if (descricao == null || descricao.isBlank()) {
            throw new DomainException("Descrição não pode ser nula ou em branco");
        }

        this.descricao = descricao;
    }
}
