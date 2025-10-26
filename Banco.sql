--Nome do banco de dados: standard
--Usuario: root
--senha: root

CREATE TABLE amigos (
    id_amigo INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(100) NOT NULL,
    telefone_usuario VARCHAR(11) NOT NULL
);

CREATE TABLE ferramentas (
    id_ferramenta  INT AUTO_INCREMENT PRIMARY KEY,
    nome_ferramenta VARCHAR(100) NOT NULL,
    marca_ferramenta VARCHAR(100),
    preco DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Emprestimos (
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
