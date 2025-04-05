CREATE TABLE pagamentos
(
    id                    BINARY(16)   NOT NULL,
    valor                 DECIMAL NOT NULL,
    nome                  VARCHAR(100) NULL,
    numero                VARCHAR(19) NULL,
    expiracao             VARCHAR(7) NULL,
    codigo                VARCHAR(3) NULL,
    status                VARCHAR(255) NOT NULL,
    pedido_id             BINARY(16) NOT NULL,
    forma_de_pagamento_id BINARY(16) NOT NULL,
    CONSTRAINT pk_pagamentos PRIMARY KEY (id)
);