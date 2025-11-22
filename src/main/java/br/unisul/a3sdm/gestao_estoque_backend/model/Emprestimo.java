package br.unisul.a3sdm.gestao_estoque_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "amigo_id")
    private Amigo amigo;

    @ManyToOne
    @JoinColumn(name = "ferramenta_id")
    private Ferramenta ferramenta;

    private LocalDate dataEmprestimo;
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
