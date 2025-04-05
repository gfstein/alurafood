package com.alurafood.pedidos.infra.mapper;

import com.alurafood.pedidos.domain.ItemDoPedido;
import com.alurafood.pedidos.domain.Pedido;
import com.alurafood.pedidos.infra.dto.CriarPedidoDto;
import com.alurafood.pedidos.infra.dto.ItemPedidoDto;
import com.alurafood.pedidos.infra.dto.ItemResponseDto;
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
    public static ItemDoPedido toItemDomainEntity(ItemPedidoDto dto) {
        return ItemDoPedido.novoItemDePedido(
                dto.quantidade(),
                dto.descricao()
        );
    }

    /**
     * Converte uma lista de ItemPedidoDto para uma lista de objetos de domínio ItemDoPedido
     */
    public static List<ItemDoPedido> toItemDomainList(List<ItemPedidoDto> dtos) {
        return dtos.stream()
                .map(PedidoMapperDto::toItemDomainEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converte um objeto de domínio ItemDoPedido para ItemResponseDto
     */
    public static ItemResponseDto toItemResponseDto(ItemDoPedido entity) {
        return new ItemResponseDto(
                entity.getId(),
                entity.getQuantidade(),
                entity.getDescricao()
        );
    }

    /**
     * Converte uma lista de objetos de domínio ItemDoPedido para uma lista de ItemResponseDto
     */
    public static List<ItemResponseDto> toItemResponseDtoList(List<ItemDoPedido> entities) {
        return entities.stream()
                .map(PedidoMapperDto::toItemResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Converte um CriarPedidoDto para uma lista de ItemDoPedido que será usado para criar um pedido
     */
    public static List<ItemDoPedido> criarPedidoDtoToItemList(CriarPedidoDto dto) {
        return toItemDomainList(dto.itens());
    }

    /**
     * Converte um objeto de domínio Pedido para PedidoResponseDto
     */
    public static PedidoResponseDto toPedidoResponseDto(Pedido entity) {
        List<ItemResponseDto> itens = toItemResponseDtoList(entity.getItens());
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

        // Converter ItemResponseDto para ItemPedidoDto para compatibilidade com o construtor
        List<ItemPedidoDto> itensPedidoDto = itens.stream()
                .map(item -> new ItemPedidoDto(item.id(), item.descricao(), item.quantidade()))
                .toList();

        return new PedidoResponseDto(
                entity.getId(),
                entity.getDataHora(),
                entity.getStatus(),
                itensPedidoDto,
                itens.size(),
                mensagem
        );
    }
}