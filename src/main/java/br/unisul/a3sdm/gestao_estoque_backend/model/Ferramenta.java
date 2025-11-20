package br.unisul.a3sdm.gestao_estoque_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ferramenta")
public class Ferramenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String marca;

    private double preco;
    
    private int quantidade_estoque;
    
    private int quantidade_minima;
    
    private int quantidade_maxima;

    private Boolean disponivel;
    
     @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
     
      @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public int getQuantidade_estoque() { return quantidade_estoque; }
    public void setQuantidade_estoque(int quantidade_estoque) { this.quantidade_estoque = quantidade_estoque; }

    public int getQuantidade_minima() { return quantidade_minima; }
    public void setQuantidade_minima(int quantidade_minima) { this.quantidade_minima = quantidade_minima; }

    public int getQuantidade_maxima() { return quantidade_maxima; }
    public void setQuantidade_maxima(int quantidade_maxima) { this.quantidade_maxima = quantidade_maxima; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
    
    public LocalDateTime getDataCadastro() {return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) {this.dataCadastro = dataCadastro; }
}

