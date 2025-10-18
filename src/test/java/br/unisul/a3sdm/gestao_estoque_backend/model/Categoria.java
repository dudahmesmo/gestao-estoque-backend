package br.unisul.a3sdm.gestao_estoque_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Anotação do Lombok: cria todos os Getters, Setters e outros métodos pra gente!
@Entity // Anotação do JPA: avisa que esta classe representa uma tabela no banco de dados.
public class Categoria {

    @Id // Avisa que o atributo 'id' é a chave primária da tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Avisa que o banco de dados vai gerar o valor do id sozinho.
    private Long id;

    private String nome;
    private String tamanho; // (Pequeno, Médio, Grande)
    private String embalagem; // (Lata, Vidro, Plástico)

}