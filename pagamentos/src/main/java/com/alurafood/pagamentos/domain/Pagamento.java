package com.alurafood.pagamentos.domain;

import com.alurafood.pagamentos.exceptions.DomainException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Pagamento {

    private UUID id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private UUID pedidoId;
    private UUID formaDePagamentoId;

    public enum Status {
        CRIADO,
        CONFIRMADO,
        CANCELADO;
    }

    private Pagamento() {
    }

    public static Pagamento create(UUID id, BigDecimal valor, String nome, String numero,
                                   String expiracao, String codigo, Status status,
                                   UUID pedidoId, UUID formaDePagamentoId
    ) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        pagamento.setValor(valor);
        pagamento.setNome(nome);
        pagamento.setNumero(numero);
        pagamento.setExpiracao(expiracao);
        pagamento.setCodigo(codigo);
        pagamento.setStatus(status != null ? status : Pagamento.Status.CRIADO);
        pagamento.setPedidoId(pedidoId);
        pagamento.setFormaDePagamentoId(formaDePagamentoId);

        return pagamento;
    }

    public static Pagamento novoPagamento(BigDecimal valor, String nome, String numero,
                                          String expiracao, String codigo,
                                          UUID pedidoId, UUID formaDePagamentoId) {
        return Pagamento.create(
                UUID.randomUUID(), valor, nome, numero, expiracao,
                codigo, Status.CRIADO, pedidoId, formaDePagamentoId);
    }


    private void setId(UUID id) {

        if (id == null) {
            throw new DomainException("Id não pode ser nulo");
        }

        this.id = id;
    }

    private void setValor(BigDecimal valor) {

        if (valor == null) {
            throw new DomainException("Valor não pode ser nulo");
        }

        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Valor não pode ser menor que zero");
        }

        this.valor = valor;
    }

    private void setNome(String nome) {

        if (nome != null && nome.length() > 100) {
            throw new DomainException("Nome não pode ter mais de 100 caracteres");
        }

        this.nome = nome;
    }

    private void setNumero(String numero) {

        if (numero != null && numero.length() > 19) {
            throw new DomainException("Numero não pode ter mais de 19 caracteres");
        }

        this.numero = numero;
    }

    private void setExpiracao(String expiracao) {
        if (expiracao != null && expiracao.length() > 7) {
            throw new DomainException("expiracao não pode ter mais de 7 caracteres");
        }

        this.expiracao = expiracao;
    }

    private void setCodigo(String codigo) {
        if (codigo != null && codigo.length() != 3) {
            throw new DomainException("codigo deve ter 3 caracteres");
        }

        this.codigo = codigo;
    }

    private void setPedidoId(UUID pedidoId) {

        if (pedidoId == null) {
            throw new DomainException("pedidoId não pode ser nulo");
        }

        this.pedidoId = pedidoId;
    }

    private void setFormaDePagamentoId(UUID formaDePagamentoId) {

        if (formaDePagamentoId == null) {
            throw new DomainException("formaDePagamentoId não pode ser nulo");
        }

        this.formaDePagamentoId = formaDePagamentoId;
    }

    private void setStatus(Status status) {

        if (status == null) {
            throw new DomainException("status não pode ser nulo");
        }

        this.status = status;
    }
}
