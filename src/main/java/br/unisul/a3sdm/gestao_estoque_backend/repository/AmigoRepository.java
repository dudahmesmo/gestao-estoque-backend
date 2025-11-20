package br.unisul.a3sdm.gestao_estoque_backend.repository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmigoRepository extends JpaRepository<Amigo, Long> {
    
    List<Amigo> findByNomeContainingIgnoreCase(String nome);
    
    List<Amigo> findByODevedorFalse();
    
    List<Amigo> findByODevedorTrue();
    
    Optional<Amigo> findByTelefone(String telefone);
    
    Optional<Amigo> findByEmail(String email);
    
    @Query("SELECT COUNT(e) > 0 FROM Emprestimo e WHERE e.amigo.id = :amigoId AND e.ativo = true")
    boolean hasEmprestimosAtivos(@Param("amigoId") Long amigoId);
   
    @Query("SELECT a FROM Amigo a WHERE a.ehDevedor = false AND " +
           "(SELECT COUNT(e) FROM Emprestimo e WHERE e.amigo.id = a.id AND e.ativo = true) < 3")
    List<Amigo> findAmigosDisponiveisParaEmprestimo();
    
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.amigo.id = :amigoId AND e.ativo = true")
    Long countEmprestimosAtivos(@Param("amigoId") Long amigoId);
}
