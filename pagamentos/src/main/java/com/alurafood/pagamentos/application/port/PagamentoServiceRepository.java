package com.alurafood.pagamentos.application.port;

import com.alurafood.pagamentos.domain.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PagamentoServiceRepository {

    Page<Pagamento> getAllPagamentos(Pageable pageable);

    Pagamento getPagamentoById(UUID id);

    Pagamento criarPagamento(Pagamento pagamento);

    Pagamento atualizarPagamento(UUID id, Pagamento pagamento);

    void excluirPagamento(UUID id);

}
