package com.alurafood.pagamentos.infra.persistence;

import com.alurafood.pagamentos.domain.Pagamento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "PAGAMENTOS")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagamentoEntity {

    @Id
    private UUID id;

    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;

    @Enumerated(EnumType.STRING)
    private Pagamento.Status status;

    private UUID pedidoId;
    private UUID formaDePagamentoId;

    public static PagamentoEntity fromModel(Pagamento pagamento) {
        Objects.requireNonNull(pagamento, "O pagamento não pode ser nulo");

        return PagamentoEntity.builder()
                .id(pagamento.getId())
                .valor(pagamento.getValor())
                .nome(pagamento.getNome())
                .numero(pagamento.getNumero())
                .expiracao(pagamento.getExpiracao())
                .codigo(pagamento.getCodigo())
                .status(pagamento.getStatus())
                .pedidoId(pagamento.getPedidoId())
                .formaDePagamentoId(pagamento.getFormaDePagamentoId())
                .build();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PagamentoEntity entity = (PagamentoEntity) o;
        return getId() != null && Objects.equals(getId(), entity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
