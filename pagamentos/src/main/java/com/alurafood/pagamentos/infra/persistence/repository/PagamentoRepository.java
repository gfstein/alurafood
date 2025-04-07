package com.alurafood.pagamentos.infra.persistence.repository;

import com.alurafood.pagamentos.infra.persistence.entitie.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, UUID> {
}
