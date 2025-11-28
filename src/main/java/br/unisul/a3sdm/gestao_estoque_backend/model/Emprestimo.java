package br.unisul.a3sdm.gestao_estoque_backend.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "amigo_id")
    @JsonBackReference("emprestimos-amigo") 
    private Amigo amigo;

    @ManyToOne
    @JoinColumn(name = "ferramenta_id")
    @JsonBackReference("emprestimos-ferramenta") 
    private Ferramenta ferramenta;

    @Column(name = "data_emprestimo", nullable = true) 
    private LocalDate dataEmprestimo;
    
    // Garante que a data de devolução (real) possa ser NULL para empréstimos ativos
    @Column(name = "data_devolucao", nullable = true)
    private LocalDate dataDevolucao; 
    
    private boolean ativo;

    public Emprestimo() {}

    public Emprestimo(Amigo amigo, Ferramenta ferramenta, LocalDate dataEmprestimo, boolean ativo) {
        this.amigo = amigo;
        this.ferramenta = ferramenta;
        this.dataEmprestimo = dataEmprestimo;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Amigo getAmigo() { return amigo; }
    public void setAmigo(Amigo amigo) { this.amigo = amigo; }

    public Ferramenta getFerramenta() { return ferramenta; }
    public void setFerramenta(Ferramenta ferramenta) { this.ferramenta = ferramenta; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}