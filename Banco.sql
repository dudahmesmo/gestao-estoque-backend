-- Nome do banco de dados: standard
-- Usuario: root
-- Senha: root

CREATE TABLE amigos (
    id_amigo INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(100) NOT NULL,
    telefone_usuario VARCHAR(11) NOT NULL,
    devedor BOOLEAN DEFAULT FALSE
);

CREATE TABLE ferramentas (
    id_ferramenta  INT AUTO_INCREMENT PRIMARY KEY,
    nome_ferramenta VARCHAR(100) NOT NULL,
    marca_ferramenta VARCHAR(100),
    preco DECIMAL(10, 2) NOT NULL,

    quantidade_estoque INT NOT NULL DEFAULT 0,
    quantidade_minima INT NOT NULL DEFAULT 0,
    quantidade_maxima INT NOT NULL DEFAULT 0
);

CREATE TABLE emprestimos (
    id_emprestimo INT AUTO_INCREMENT PRIMARY KEY,
    id_ferramenta INT NOT NULL,
    nome_ferramenta VARCHAR(100) NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao_esperada DATE NOT NULL,
    id_amigo INT NOT NULL,
    nome_usuario VARCHAR(100) NOT NULL,
    telefone_usuario VARCHAR(11) NOT NULL,
    status_emprestimo VARCHAR(20) NOT NULL,

    CONSTRAINT fk_amigo_emprestimos FOREIGN KEY (id_amigo) REFERENCES amigos(id_amigo) ON DELETE CASCADE,
    CONSTRAINT fk_ferramenta_emprestimos FOREIGN KEY (id_ferramenta) REFERENCES ferramentas(id_ferramenta) ON DELETE CASCADE
);
