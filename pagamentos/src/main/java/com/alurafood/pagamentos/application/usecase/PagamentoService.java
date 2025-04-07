package com.alurafood.pagamentos.application.usecase;

import com.alurafood.pagamentos.application.port.PagamentoServiceRepository;
import com.alurafood.pagamentos.application.port.PedidoNotificacao;
import com.alurafood.pagamentos.domain.Pagamento;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoServiceRepository repository;
    private final PedidoNotificacao pedidoNotificacao;

    public Page<Pagamento> getAllPagamentos(Pageable pageable) {
        return repository.getAllPagamentos(pageable);
    }

    public Pagamento getPagamentoById(UUID id) {
        return repository.getPagamentoById(id);
    }

    public Pagamento criarPagamento(Pagamento pagamento) {
        return repository.criarPagamento(pagamento);
    }

    public Pagamento atualizarPagamento(UUID id, Pagamento pagamento) {
        return repository.atualizarPagamento(id, pagamento);
    }

    public void excluirPagamento(UUID id) {
        repository.excluirPagamento(id);
    }

    @Transactional
    protected Pagamento processarConfirmacaoPagamento(UUID pagamentoId, Consumer<Pagamento> estrategiaConfirmacao) {
        Pagamento pagamento = repository.getPagamentoById(pagamentoId);
        estrategiaConfirmacao.accept(pagamento);
        repository.atualizarPagamento(pagamentoId, pagamento);
        return pagamento;
    }

    @Transactional
    public void confirmarPagamento(UUID pagamentoId) {
        Pagamento pagamento = processarConfirmacaoPagamento(pagamentoId, Pagamento::confirmarPagamento);
        pedidoNotificacao.notificarPagamentoConcluido(pagamento.getPedidoId());
    }

    @Transactional
    public void confirmarPagamentoFallback(UUID pagamentoId) {
        processarConfirmacaoPagamento(pagamentoId, Pagamento::confirmarPagamentoSemIntegracao);
    }


}
