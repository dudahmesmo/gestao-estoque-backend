-- Nome do Banco: gestao_estoque
-- Usuário: root
-- Senha: DataBaseA3

CREATE DATABASE IF NOT EXISTS `gestao_estoque` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `gestao_estoque`;

--
-- Tabela `amigo`
--
DROP TABLE IF EXISTS `amigo`;
CREATE TABLE `amigo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_cadastro` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `odevedor` bit(1) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Tabela `categoria`
--
DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Tabela `emprestimos`
--
DROP TABLE IF EXISTS `emprestimos`;
CREATE TABLE `emprestimos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `data_devolucao` date DEFAULT NULL,
  `data_emprestimo` date DEFAULT NULL,
  `amigo_id` bigint DEFAULT NULL,
  `ferramenta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK758vvt4j2yhmksidn7gl1u6hv` (`amigo_id`),
  KEY `FKp0amelfvl1k7x9xwowtj77q2e` (`ferramenta_id`),
  CONSTRAINT `FK758vvt4j2yhmksidn7gl1u6hv` FOREIGN KEY (`amigo_id`) REFERENCES `amigo` (`id`),
  CONSTRAINT `FKp0amelfvl1k7x9xwowtj77q2e` FOREIGN KEY (`ferramenta_id`) REFERENCES `ferramenta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Tabela `ferramenta`
--
DROP TABLE IF EXISTS `ferramenta`;
CREATE TABLE `ferramenta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `disponivel` bit(1) DEFAULT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `data_cadastro` datetime(6) DEFAULT NULL,
  `preco` double NOT NULL,
  `emprestada` bit(1) DEFAULT NULL,
  `quantidade_estoque` int DEFAULT NULL,
  `quantidade_maxima_estoque` int DEFAULT NULL,
  `quantidade_minima_estoque` int DEFAULT NULL,
  `id_categoria` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrna9nq45adf90qri5djydwabu` (`id_categoria`),
  CONSTRAINT `FKrna9nq45adf90qri5djydwabu` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;