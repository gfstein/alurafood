package com.alurafood.pagamentos.infra.persistence;

import com.alurafood.pagamentos.application.PagamentoServiceRepository;
import com.alurafood.pagamentos.domain.Pagamento;
import com.alurafood.pagamentos.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PagamentoRepositoryJpa implements PagamentoServiceRepository {

    private final PagamentoRepository repository;

    public PagamentoRepositoryJpa(PagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Pagamento> getAllPagamentos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(Pagamento::of);
    }

    @Override
    public Pagamento getPagamentoById(UUID id) {
        return repository.findById(id)
                .map(Pagamento::of)
                .orElseThrow(() -> new NotFoundException(String.format("Pagamento com id %s não encontrado", id)));
    }

    @Override
    public Pagamento criarPagamento(Pagamento pagamento) {
        return Pagamento.of(repository.save(PagamentoEntity.fromModel(pagamento)));
    }

    @Override
    public Pagamento atualizarPagamento(UUID id, Pagamento pagamento) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Pagamento não encontrado com o ID: " + id);
        }

        PagamentoEntity entity = PagamentoEntity.fromModel(pagamento);
        entity.setId(id);
        return Pagamento.of(repository.save(entity));
    }

    @Override
    public void excluirPagamento(UUID id) {
        repository.deleteById(id);
    }
}
