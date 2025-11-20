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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

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

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
