package com.alurafood.pedidos.domain;

import com.alurafood.pedidos.exceptions.DomainException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ItemPedido {

    private UUID id;
    private Integer quantidade;
    private String descricao;
    private BigDecimal valor;
    
    private ItemPedido() {}

    public static ItemPedido create(UUID id, Integer quantidade, String descricao, BigDecimal valor) {
        ItemPedido item = new ItemPedido();
        item.setId(id);
        item.setQuantidade(quantidade);
        item.setDescricao(descricao);
        item.setValor(valor);
        return item;
    }

    public static ItemPedido novoItemDePedido(Integer quantidade, String descricao, BigDecimal valor) {
        return ItemPedido.create(UUID.randomUUID(), quantidade, descricao, valor);
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

    private void setValor(BigDecimal valor) {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Valor não pode ser nulo ou menor que zero");
        }

        this.valor = valor;
    }
}
