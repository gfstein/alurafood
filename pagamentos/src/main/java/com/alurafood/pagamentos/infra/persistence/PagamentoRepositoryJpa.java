package com.alurafood.pagamentos.infra.persistence;

import com.alurafood.pagamentos.application.PagamentoServiceRepository;
import com.alurafood.pagamentos.domain.Pagamento;
import com.alurafood.pagamentos.exceptions.NotFoundException;
import com.alurafood.pagamentos.infra.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PagamentoRepositoryJpa implements PagamentoServiceRepository {

    private final PagamentoRepository repository;

    @Override
    public Page<Pagamento> getAllPagamentos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(PagamentoMapper::fromEntity);
    }

    @Override
    public Pagamento getPagamentoById(UUID id) {
        return repository.findById(id)
                .map(PagamentoMapper::fromEntity)
                .orElseThrow(() -> new NotFoundException(String.format("Pagamento com id %s não encontrado", id)));
    }

    @Override
    public Pagamento criarPagamento(Pagamento pagamento) {
        return PagamentoMapper.fromEntity(repository.save(PagamentoMapper.toEntity(pagamento)));
    }

    @Override
    public Pagamento atualizarPagamento(UUID id, Pagamento pagamento) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Pagamento não encontrado com o ID: " + id);
        }

        PagamentoEntity entity = PagamentoMapper.toEntity(pagamento);
        entity.setId(id);
        return PagamentoMapper.fromEntity(repository.save(entity));
    }

    @Override
    public void excluirPagamento(UUID id) {
        repository.deleteById(id);
    }
}
