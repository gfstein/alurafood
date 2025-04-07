package com.alurafood.pagamentos.infra.controller;

import com.alurafood.pagamentos.application.usecase.PagamentoService;
import com.alurafood.pagamentos.domain.Pagamento;
import com.alurafood.pagamentos.infra.dto.PagamentoDto;
import com.alurafood.pagamentos.infra.mapper.PagamentoMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Slf4j
@RestController
@RequestMapping("/pagamentos")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService service;

    @GetMapping
    public Page<PagamentoDto> listar(@PageableDefault() Pageable pageable) {
        return service.getAllPagamentos(pageable)
                .map(PagamentoMapper::toDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(PagamentoMapper.toDto(service.getPagamentoById(id)));
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
        Pagamento pagamento = service.criarPagamento(PagamentoMapper.fromDto(dto));
        URI uri = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();
        return ResponseEntity.created(uri).body(PagamentoMapper.toDto(pagamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull UUID id, @RequestBody @Valid PagamentoDto dto) {
        return ResponseEntity.ok(PagamentoMapper.toDto(service.atualizarPagamento(id, PagamentoMapper.fromDto(dto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable @NotNull UUID id) {
        service.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(@PathVariable @NotNull UUID id){
        service.confirmarPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(UUID id, Exception e) {
        log.error("Erro ao confirmar pagamento com id {}", id, e);
        service.confirmarPagamentoFallback(id);
    }

}
