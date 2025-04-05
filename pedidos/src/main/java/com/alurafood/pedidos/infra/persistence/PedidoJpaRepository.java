package com.alurafood.pedidos.infra.persistence;

import com.alurafood.pedidos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, UUID> {
    
    @Modifying
    @Query("UPDATE PedidoEntity p SET p.status = :status WHERE p.id = :id")
    void atualizarStatus(UUID id, Pedido.Status status);
}