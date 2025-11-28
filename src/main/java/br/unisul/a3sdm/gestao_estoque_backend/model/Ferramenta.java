package br.unisul.a3sdm.gestao_estoque_backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table; 
@Entity
@Table (name = "ferramenta")
@JsonIdentityInfo (generator =
ObjectIdGenerators. PropertyGenerator.class, property = "id")
public class Ferramenta {

    @Id
    @GeneratedValue (strategy = GenerationType. IDENTITY)
    private Long id;
<<<<<<< HEAD

    @Column(nullable = false)
=======
>>>>>>> develop
    private String nome;
    private String marca;
    private Double preco;
    
    // Colunas do banco
    @Column (name = "quantidade_estoque")
    private Integer quantidadeEstoque;
    @Column(name = "quantidade_minima_estoque")
    private Integer quantidadeMinimaEstoque;
    @Column(name = "quantidade_maxima_estoque")
    private Integer quantidadeMaximaEstoque;
    
    private Boolean disponivel = true;
    
    @ManyToOne
    @JoinColumn (name = "id_categoria", referencedColumnName = "id")
    @JsonBackReference 
    private Categoria categoria;
    
    @OneToMany(mappedBy = "ferramenta", cascade = CascadeType.ALL)
    @JsonManagedReference("emprestimos-ferramenta") 
    private List<Emprestimo> emprestimos;
    
    public Ferramenta () {}
    
    // GETTERS E SETTERS 
    public Long getId() { return id; }
    public void setId (Long id) { this.id = id; }
    
    public String getNome () { return nome; }
    public void setNome (String nome) { this.nome = nome; }
    
    public String getMarca() { return marca; }
    public void setMarca (String marca) { this.marca = marca; }
    
    public Double getPreco() { return preco; }
    public void setPreco (Double preco) { this.preco = preco; }
    
    @JsonProperty("Quantidade_estoque") 
    public Integer getQuantidadeEstoque () { return quantidadeEstoque; }
    
    @JsonProperty("Quantidade_estoque")
    public void setQuantidadeEstoque (Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    } 
    
    @JsonProperty("Quantidade_minima")
    public Integer getQuantidadeMinimaEstoque () { return quantidadeMinimaEstoque; }
    
    @JsonProperty("Quantidade_minima")
    public void setQuantidadeMinimaEstoque (Integer quantidadeMinimaEstoque) { 
        this.quantidadeMinimaEstoque = quantidadeMinimaEstoque;
    }
    
    @JsonProperty("Quantidade_maxima")
    public Integer getQuantidadeMaximaEstoque() { return quantidadeMaximaEstoque; }
    
    @JsonProperty("Quantidade_maxima")
    public void setQuantidadeMaximaEstoque (Integer quantidadeMaximaEstoque) { 
        this.quantidadeMaximaEstoque = quantidadeMaximaEstoque;
    }
    
    public Integer getQuantidadeMinima () { return quantidadeMinimaEstoque; }
    public void setQuantidadeMinima (Integer quantidadeMinima) { this.quantidadeMinimaEstoque = quantidadeMinima; }
    public Integer getQuantidadeMaxima () { return quantidadeMaximaEstoque; }
    public void setQuantidadeMaxima (Integer quantidadeMaxima) { this.quantidadeMaximaEstoque = quantidadeMaxima; }
    
    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel (Boolean disponivel) { this.disponivel = disponivel; }
    
    public Categoria getCategoria() { return categoria; }
    public void setCategoria (Categoria categoria) { this.categoria = categoria; }
    
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
}