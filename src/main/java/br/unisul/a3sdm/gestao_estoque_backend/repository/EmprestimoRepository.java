package br.unisul.a3sdm.gestao_estoque_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import br.unisul.a3sdm.gestao_estoque_backend.model.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByAtivoTrue();

    @Query("SELECT e FROM Emprestimo e WHERE e.ativo = true AND (e.dataDevolucao IS NULL OR e.dataDevolucao < :dataAtual)")
    List<Emprestimo> findByAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(@Param("dataAtual") LocalDate dataAtual);

    @Query("SELECT e FROM Emprestimo e WHERE e.amigo = :amigo AND e.ativo = true AND (e.dataDevolucao IS NULL OR e.dataDevolucao < :dataAtual)")
    List<Emprestimo> findByAmigoAndAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(
            @Param("amigo") Amigo amigo, @Param("dataAtual") LocalDate dataAtual);

    // Métodos para Relatório de Histórico
    // Retorna a lista de ferramentas ordenadas pela mais emprestada (Saída).
    @Query("SELECT e.ferramenta.nome, COUNT(e) AS totalEmprestimos FROM Emprestimo e GROUP BY e.ferramenta.nome ORDER BY totalEmprestimos DESC")
    List<Object[]> findFerramentaMaisEmprestada();

    // Retorna a lista de ferramentas ordenadas pela mais devolvida (Entrada).
    // Filtra apenas os empréstimos que já possuem data de devolução.
    @Query("SELECT e.ferramenta.nome, COUNT(e) AS totalDevolucoes FROM Emprestimo e WHERE e.dataDevolucao IS NOT NULL GROUP BY e.ferramenta.nome ORDER BY totalDevolucoes DESC")
    List<Object[]> findFerramentaMaisDevolvida();

    @Query("SELECT e FROM Emprestimo e WHERE e.ativo = true AND (e.dataDevolucao IS NULL OR e.dataDevolucao < :dataAtual)")
    List<Emprestimo> findEmprestimosEmAtraso(@Param("dataAtual") LocalDate dataAtual); // Renomeado
}
