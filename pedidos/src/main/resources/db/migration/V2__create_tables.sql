CREATE TABLE itens_do_pedido
(
    id         BINARY(16)   NOT NULL,
    descricao  VARCHAR(255) NOT NULL,
    quantidade INT          NOT NULL,
    pedido_id  BINARY(16)   NOT NULL,
    CONSTRAINT pk_itens_do_pedido PRIMARY KEY (id)
);

CREATE TABLE pedidos
(
    id        BINARY(16)   NOT NULL,
    data_hora datetime     NOT NULL,
    status    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_pedidos PRIMARY KEY (id)
);

ALTER TABLE itens_do_pedido
    ADD CONSTRAINT FK_ITENS_DO_PEDIDO_ON_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedidos (id);