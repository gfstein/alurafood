package com.alurafood.pedidos.infra.mapper;

import com.alurafood.pedidos.domain.ItemPedido;
import com.alurafood.pedidos.domain.Pedido;
import com.alurafood.pedidos.infra.dto.ItemPedidoDto;
import com.alurafood.pedidos.infra.dto.PedidoResponseDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por mapear objetos entre a camada de domínio e a camada de apresentação (DTOs).
 * Utiliza métodos estáticos para facilitar o uso em qualquer parte da aplicação.
 */
public class PedidoMapperDto {

    private PedidoMapperDto() {
        // Construtor privado para evitar instanciação
    }

    /**
     * Converte um ItemPedidoDto para um objeto de domínio ItemDoPedido
     */
    public static ItemPedido toItemDomainEntity(ItemPedidoDto dto) {
        return ItemPedido.novoItemDePedido(
                dto.quantidade(),
                dto.descricao(),
                dto.valor()
        );
    }

    /**
     * Converte uma lista de ItemPedidoDto para uma lista de objetos de domínio ItemDoPedido
     */
    public static List<ItemPedido> toItemDomainList(List<ItemPedidoDto> dtos) {
        return dtos.stream()
                .map(PedidoMapperDto::toItemDomainEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converte um objeto de domínio ItemDoPedido para ItemPedidoDto
     */
    public static ItemPedidoDto toItemPedidoDto(ItemPedido entity) {
        return new ItemPedidoDto(
                entity.getId(),
                entity.getDescricao(),
                entity.getQuantidade(),
                entity.getValor()
        );
    }

    /**
     * Converte uma lista de objetos de domínio ItemDoPedido para uma lista de ItemPedidoDto
     */
    public static List<ItemPedidoDto> toItemPedidoDtoList(List<ItemPedido> entities) {
        return entities.stream()
                .map(PedidoMapperDto::toItemPedidoDto)
                .collect(Collectors.toList());
    }

    /**
     * Converte um objeto de domínio Pedido para PedidoResponseDto
     */
    public static PedidoResponseDto toPedidoResponseDto(Pedido entity) {
        List<ItemPedidoDto> itens = toItemPedidoDtoList(entity.getItens());
        String mensagem = switch (entity.getStatus()) {
            case REALIZADO -> "Pedido realizado com sucesso!";
            case PAGO -> "Pagamento aprovado!";
            case NAO_AUTORIZADO -> "Pagamento não autorizado!";
            case CONFIRMADO -> "Pedido confirmado e em preparação!";
            case PRONTO -> "Pedido pronto para retirada!";
            case SAIU_PARA_ENTREGA -> "Pedido saiu para entrega!";
            case ENTREGUE -> "Pedido entregue!";
            case CANCELADO -> "Pedido cancelado.";
        };

        List<ItemPedidoDto> itensPedidoDto = itens.stream()
                .map(item -> new ItemPedidoDto(item.id(), item.descricao(), item.quantidade(), item.valor()))
                .toList();

        return new PedidoResponseDto(
                entity.getId(),
                entity.getDataHora(),
                entity.getStatus(),
                itensPedidoDto,
                itens.size(),
                entity.getTotal(),
                mensagem
        );
    }
}