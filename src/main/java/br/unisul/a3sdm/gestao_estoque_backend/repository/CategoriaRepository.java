package br.unisul.a3sdm.gestao_estoque_backend.repository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNome(String nome);
    
    List<Categoria> findAllByOrderByNomeAsc();
    
    boolean existsByNome(String nome);
    
    // Verifica se existe categoria com mesmo nome, excluindo um ID específico
    @Query("SELECT COUNT(c) > 0 FROM Categoria c WHERE c.nome = :nome AND c.id != :id")
    boolean existsByNomeAndIdNot(String nome, Long id);
    
    // Busca categorias por parte do nome (para filtros)
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}
