package br.unisul.a3sdm.gestao_estoque_backend.repository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Emprestimo;
import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    
    List<Emprestimo> findByAtivoTrue();
    
    @Query("SELECT e FROM Emprestimo e WHERE e.ativo = true AND (e.dataDevolucao IS NULL OR e.dataDevolucao < :dataAtual)")
    List<Emprestimo> findByAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(@Param("dataAtual") LocalDate dataAtual);
    
    @Query("SELECT e FROM Emprestimo e WHERE e.amigo = :amigo AND e.ativo = true AND (e.dataDevolucao IS NULL OR e.dataDevolucao < :dataAtual)")
    List<Emprestimo> findByAmigoAndAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(
            @Param("amigo") Amigo amigo, @Param("dataAtual") LocalDate dataAtual);
}

