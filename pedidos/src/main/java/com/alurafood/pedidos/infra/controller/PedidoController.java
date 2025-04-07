package com.alurafood.pedidos.infra.controller;

import com.alurafood.pedidos.application.PedidoService;
import com.alurafood.pedidos.domain.ItemPedido;
import com.alurafood.pedidos.domain.Pedido;
import com.alurafood.pedidos.infra.dto.AtualizaStatusDto;
import com.alurafood.pedidos.infra.dto.ItemPedidoDto;
import com.alurafood.pedidos.infra.dto.PedidoResponseDto;
import com.alurafood.pedidos.infra.mapper.PedidoMapperDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> criarPedido(
            @RequestBody @Valid List<ItemPedidoDto> dto,
            UriComponentsBuilder uriBuilder) {

        // Usar o mapper para converter o DTO para objetos de domínio
        List<ItemPedido> itens = PedidoMapperDto.toItemDomainList(dto);

        Pedido pedidoCriado = pedidoService.criarPedido(itens);

        URI location = uriBuilder
                .path("/pedidos/{id}")
                .buildAndExpand(pedidoCriado.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(PedidoMapperDto.toPedidoResponseDto(pedidoCriado));
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDto>> listarPedidos(
            @PageableDefault(size = 10, sort = "dataHora", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable paginacao) {

        Page<Pedido> pedidos = pedidoService.listarTodos(paginacao);
        Page<PedidoResponseDto> dtoPage = pedidos.map(PedidoMapperDto::toPedidoResponseDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPedidoPorId(@PathVariable UUID id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(PedidoMapperDto.toPedidoResponseDto(pedido));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody @Valid AtualizaStatusDto dto) {

        pedidoService.atualizarStatusPedido(id, dto.status());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<PedidoResponseDto> adicionarItem(
            @PathVariable UUID id,
            @RequestBody @Valid ItemPedidoDto itemDto,
            UriComponentsBuilder uriBuilder) {

        // Usar o mapper para converter o DTO para objeto de domínio
        ItemPedido item = PedidoMapperDto.toItemDomainEntity(itemDto);

        Pedido pedidoAtualizado = pedidoService.adicionarItemAoPedido(id, item);

        URI location = uriBuilder
                .path("/pedidos/{id}")
                .buildAndExpand(pedidoAtualizado.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(PedidoMapperDto.toPedidoResponseDto(pedidoAtualizado));
    }

    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<PedidoResponseDto> removerItem(
            @PathVariable UUID pedidoId,
            @PathVariable UUID itemId) {

        Pedido pedidoAtualizado = pedidoService.removerItemDoPedido(pedidoId, itemId);
        return ResponseEntity.ok(PedidoMapperDto.toPedidoResponseDto(pedidoAtualizado));
    }

    @PutMapping("/{id}/pago")
    public ResponseEntity<PedidoResponseDto> aprovarPagamento(@PathVariable UUID id) {
        Pedido pedidoAtualizado = pedidoService.aprovaPagamentoPedido(id);
        return ResponseEntity.ok(PedidoMapperDto.toPedidoResponseDto(pedidoAtualizado));
    }
}