package com.alurafood.pagamentos.infra.mapper;

import com.alurafood.pagamentos.domain.Pagamento;
import com.alurafood.pagamentos.infra.dto.PagamentoDto;
import com.alurafood.pagamentos.infra.persistence.PagamentoEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por converter entre os diferentes modelos de Pagamento:
 * - Domínio (Pagamento)
 * - DTO (PagamentoDto)
 * - Entidade (PagamentoEntity)
 */
public class PagamentoMapper {

    private PagamentoMapper() {
        // Construtor privado para evitar instanciação
    }

    /**
     * Converte uma entidade de persistência para um objeto de domínio
     */
    public static Pagamento fromEntity(PagamentoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Pagamento.create(
            entity.getId(),
            entity.getValor(),
            entity.getNome(),
            entity.getNumero(),
            entity.getExpiracao(),
            entity.getCodigo(),
            entity.getStatus(),
            entity.getPedidoId(),
            entity.getFormaDePagamentoId()
        );
    }
    
    /**
     * Converte uma lista de entidades para uma lista de objetos de domínio
     */
    public static List<Pagamento> fromEntityList(List<PagamentoEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        
        return entities.stream()
            .map(PagamentoMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    /**
     * Converte um DTO para um objeto de domínio
     * Se o ID for nulo, cria um novo pagamento
     */
    public static Pagamento fromDto(PagamentoDto dto) {
        if (dto == null) {
            return null;
        }
        
        if (dto.id() == null) {
            return Pagamento.novoPagamento(
                dto.valor(),
                dto.nome(),
                dto.numero(),
                dto.expiracao(),
                dto.codigo(),
                dto.pedidoId(),
                dto.formaDePagamentoId()
            );
        }
        
        return Pagamento.create(
            dto.id(),
            dto.valor(),
            dto.nome(),
            dto.numero(),
            dto.expiracao(),
            dto.codigo(),
            dto.status(),
            dto.pedidoId(),
            dto.formaDePagamentoId()
        );
    }
    
    /**
     * Converte um objeto de domínio para uma entidade de persistência
     */
    public static PagamentoEntity toEntity(Pagamento domain) {
        if (domain == null) {
            return null;
        }
        
        PagamentoEntity entity = new PagamentoEntity();
        entity.setId(domain.getId());
        entity.setValor(domain.getValor());
        entity.setNome(domain.getNome());
        entity.setNumero(domain.getNumero());
        entity.setExpiracao(domain.getExpiracao());
        entity.setCodigo(domain.getCodigo());
        entity.setStatus(domain.getStatus());
        entity.setPedidoId(domain.getPedidoId());
        entity.setFormaDePagamentoId(domain.getFormaDePagamentoId());
        
        return entity;
    }
    
    /**
     * Converte uma lista de objetos de domínio para uma lista de entidades
     */
    public static List<PagamentoEntity> toEntityList(List<Pagamento> domains) {
        if (domains == null) {
            return List.of();
        }
        
        return domains.stream()
            .map(PagamentoMapper::toEntity)
            .collect(Collectors.toList());
    }
    
    /**
     * Converte um objeto de domínio para um DTO
     */
    public static PagamentoDto toDto(Pagamento domain) {
        if (domain == null) {
            return null;
        }
        
        return new PagamentoDto(
            domain.getId(),
            domain.getValor(),
            domain.getNome(),
            domain.getNumero(),
            domain.getExpiracao(),
            domain.getCodigo(),
            domain.getStatus(),
            domain.getPedidoId(),
            domain.getFormaDePagamentoId()
        );
    }
    
    /**
     * Converte uma lista de objetos de domínio para uma lista de DTOs
     */
    public static List<PagamentoDto> toDtoList(List<Pagamento> domains) {
        if (domains == null) {
            return List.of();
        }
        
        return domains.stream()
            .map(PagamentoMapper::toDto)
            .collect(Collectors.toList());
    }
}