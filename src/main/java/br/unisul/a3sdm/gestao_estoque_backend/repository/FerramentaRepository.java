package br.unisul.a3sdm.gestao_estoque_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
public interface FerramentaRepository extends JpaRepository<Ferramenta, Long> {
    

    List<Ferramenta> findByDisponivelTrue();

    List<Ferramenta> findByPrecoLessThan(Double preco);

    List<Ferramenta> findByMarcaContainingIgnoreCase(String marca);

    List<Ferramenta> findByCategoriaId(Long categoriaId);

    List<Ferramenta> findByCategoriaIsNull();

    @Query("SELECT f.categoria.nome, COUNT(f.id) FROM Ferramenta f GROUP BY f.categoria.nome ORDER BY COUNT(f.id) DESC")
    List<Object[]> findQuantidadeProdutosPorCategoria();
}
