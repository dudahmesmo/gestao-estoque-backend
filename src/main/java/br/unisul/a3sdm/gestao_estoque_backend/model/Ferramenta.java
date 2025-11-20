package br.unisul.a3sdm.gestao_estoque_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ferramentas")
public class Ferramenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ferramenta")
    private Long id;

    @Column(name = "nome_ferramenta", nullable = false)
    private String nome;

    @Column(name = "marca_ferramenta")
    private String marca;

<<<<<<< HEAD
    @Column(nullable = false)
    private double preco;

    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    @Column(name = "quantidade_minima", nullable = false)
    private int quantidadeMinima;

    @Column(name = "quantidade_maxima", nullable = false)
    private int quantidadeMaxima;

    private Boolean disponivel;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    // GETTERS E SETTERS ----------------------

=======
    private Double preco;

    private Integer quantidade;

    private Integer quantidadeEstoque;
    private Integer quantidadeMinimaEstoque;
    private Integer quantidadeMaximaEstoque;

    @Column(name = "emprestada")
    private Boolean emprestada = false;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    private Boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

    public Ferramenta() {}

    // GETTERS E SETTERS

>>>>>>> develop
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

<<<<<<< HEAD
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public int getQuantidadeMinima() { return quantidadeMinima; }
    public void setQuantidadeMinima(int quantidadeMinima) { this.quantidadeMinima = quantidadeMinima; }

    public int getQuantidadeMaxima() { return quantidadeMaxima; }
    public void setQuantidadeMaxima(int quantidadeMaxima) { this.quantidadeMaxima = quantidadeMaxima; }
=======
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public Integer getQuantidadeMinimaEstoque() { return quantidadeMinimaEstoque; }
    public void setQuantidadeMinimaEstoque(Integer quantidadeMinimaEstoque) { this.quantidadeMinimaEstoque = quantidadeMinimaEstoque; }

    public Integer getQuantidadeMaximaEstoque() { return quantidadeMaximaEstoque; }
    public void setQuantidadeMaximaEstoque(Integer quantidadeMaximaEstoque) { this.quantidadeMaximaEstoque = quantidadeMaximaEstoque; }

    public Boolean getEmprestada() { return emprestada; }
    public void setEmprestada(Boolean emprestada) { this.emprestada = emprestada; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
>>>>>>> develop

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }

<<<<<<< HEAD
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
=======
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
>>>>>>> develop
}
