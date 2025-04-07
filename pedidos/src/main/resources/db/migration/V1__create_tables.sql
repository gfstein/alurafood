CREATE TABLE itens_pedido
(
    id         BINARY(16)   NOT NULL,
    descricao  VARCHAR(255)   NOT NULL,
    quantidade INT            NOT NULL,
    valor      DECIMAL(19, 2) NOT NULL,
    pedido_id  BINARY(16)   NOT NULL,
    CONSTRAINT pk_itens_pedido PRIMARY KEY (id)
);

CREATE TABLE pedidos
(
    id        BINARY(16)   NOT NULL,
    data_hora datetime       NOT NULL,
    status    VARCHAR(255)   NOT NULL,
    total     DECIMAL(19, 2) NOT NULL,
    CONSTRAINT pk_pedidos PRIMARY KEY (id)
);

ALTER TABLE itens_pedido
    ADD CONSTRAINT FK_ITENS_PEDIDO_ON_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedidos (id);