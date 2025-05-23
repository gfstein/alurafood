package com.alurafood.pedidos.domain;

import com.alurafood.pedidos.exceptions.DomainException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Pedido {

    private UUID id;
    private LocalDateTime dataHora;
    private Status status;
    private List<ItemPedido> itens = new ArrayList<>();
    private BigDecimal total;

    private Pedido() {
    }

    public static Pedido create(UUID id, LocalDateTime dataHora, Status status, List<ItemPedido> itens) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setDataHora(dataHora);
        pedido.setItens(itens);
        pedido.setStatus(status);
        return pedido;
    }

    public static Pedido novoPedido(List<ItemPedido> itens) {
        return Pedido.create(UUID.randomUUID(), LocalDateTime.now(), Status.REALIZADO, itens);
    }

    private void setId(UUID id) {
        if (id == null) {
            throw new DomainException("Id não pode ser nulo");
        }
        this.id = id;
    }

    private void setDataHora(LocalDateTime dataHora) {
        if (dataHora == null || dataHora.isAfter(LocalDateTime.now())) {
            throw new DomainException("Data e hora não podem ser nulas ou no futuro");
        }
        this.dataHora = dataHora;
    }

    private void setStatus(Status status) {
        
        if (status == null) {
            throw new DomainException("Status não pode ser nulo");
        }
        
        this.status = status;
    }

    private void setItens(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new DomainException("Itens do pedido não podem ser nulos ou vazios");
        }
        this.itens = itens;
        calcularTotal();
    }

    private void calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : this.itens) {
            total = total.add(item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        this.total = total;
    }

    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new DomainException("Item do pedido não pode ser nulo");
        }
        this.itens.add(item);
        calcularTotal();
    }

    public void removerItem(UUID itemId) {
        if (itemId == null) {
            throw new DomainException("Id do item não pode ser nulo");
        }

        boolean removed = this.itens.removeIf(item -> item.getId().equals(itemId));

        if (!removed) {
            throw new DomainException("Item com o id especificado não encontrado no pedido");
        }
        calcularTotal();
    }

    public void aprovarPagamento() {
        if (this.status == Status.CANCELADO ||
                this.status == Status.PAGO ||
                this.status == Status.NAO_AUTORIZADO) {
            throw new DomainException("Não é possível aprovar pagamento do pedido no status: " + this.status);
        }

        this.status = Status.PAGO;
    }

    public enum Status {
        REALIZADO,
        CANCELADO,
        PAGO,
        NAO_AUTORIZADO,
        CONFIRMADO,
        PRONTO,
        SAIU_PARA_ENTREGA,
        ENTREGUE;
    }

}
