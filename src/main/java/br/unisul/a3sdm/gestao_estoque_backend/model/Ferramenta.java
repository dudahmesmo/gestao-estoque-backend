package br.unisul.a3sdm.gestao_estoque_backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ferramenta")
public class Ferramenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String marca;

    private Double preco;

    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque = 0;

    @Column(name = "quantidade_minima_estoque")
    private Integer quantidadeMinimaEstoque = 1;

    @Column(name = "quantidade_maxima_estoque")
    private Integer quantidadeMaximaEstoque = 100;

    private Boolean disponivel = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @JsonIgnoreProperties("ferramentas") // Evita recursão infinita no JSON
    private Categoria categoria;

    public Ferramenta() {}

    // Construtor com parâmetros
    public Ferramenta(String nome, String marca, Double preco, Integer quantidadeEstoque, 
                     Integer quantidadeMinima, Integer quantidadeMaxima, Categoria categoria) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinimaEstoque = quantidadeMinima;
        this.quantidadeMaximaEstoque = quantidadeMaxima;
        this.categoria = categoria;
        this.disponivel = quantidadeEstoque > 0;
    }

    // GETTERS E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { 
        this.quantidadeEstoque = quantidadeEstoque; 
        // Atualiza automaticamente a disponibilidade
        if (this.quantidadeEstoque != null && this.quantidadeEstoque <= 0) {
            this.disponivel = false;
        } else {
            this.disponivel = true;
        }
    }

    public Integer getQuantidadeMinimaEstoque() { return quantidadeMinimaEstoque; }
    public void setQuantidadeMinimaEstoque(Integer quantidadeMinimaEstoque) { 
        this.quantidadeMinimaEstoque = quantidadeMinimaEstoque; 
    }

    public Integer getQuantidadeMaximaEstoque() { return quantidadeMaximaEstoque; }
    public void setQuantidadeMaximaEstoque(Integer quantidadeMaximaEstoque) { 
        this.quantidadeMaximaEstoque = quantidadeMaximaEstoque; 
    }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    // MÉTODOS DE NEGÓCIO
    public boolean isEstoqueBaixo() {
        return quantidadeEstoque != null && quantidadeMinimaEstoque != null 
            && quantidadeEstoque <= quantidadeMinimaEstoque;
    }

    public boolean isEstoqueExcedido() {
        return quantidadeEstoque != null && quantidadeMaximaEstoque != null 
            && quantidadeEstoque > quantidadeMaximaEstoque;
    }

    public String getStatusEstoque() {
        if (quantidadeEstoque == null || quantidadeEstoque <= 0) {
            return "FORA DE ESTOQUE";
        } else if (isEstoqueBaixo()) {
            return "ESTOQUE BAIXO (" + quantidadeEstoque + " unidades)";
        } else if (isEstoqueExcedido()) {
            return "ESTOQUE EXCEDIDO (" + quantidadeEstoque + " unidades)";
        } else {
            return quantidadeEstoque + " unidades (OK)";
        }
    }

    // Métodos de conveniência para compatibilidade com o front-end
    public String getNomeCategoria() {
        return categoria != null ? categoria.getNome() : "Sem categoria";
    }

    @Override
    public String toString() {
        return "Ferramenta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", marca='" + marca + '\'' +
                ", preco=" + preco +
                ", categoria=" + (categoria != null ? categoria.getNome() : "N/A") +
                '}';
    }
}