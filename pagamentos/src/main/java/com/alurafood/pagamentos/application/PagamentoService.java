package com.alurafood.pagamentos.application;

import com.alurafood.pagamentos.domain.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PagamentoService {

    private final PagamentoServiceRepository repository;

    public PagamentoService(PagamentoServiceRepository repository) {
        this.repository = repository;
    }

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

}
