package br.unisul.a3sdm.gestao_estoque_backend.repository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FerramentaRepository extends JpaRepository<Ferramenta, Long> {
    
    List<Ferramenta> findByDisponivelTrue();
    
    List<Ferramenta> findByPrecoLessThan(double preco);
    
    List<Ferramenta> findByMarcaContainingIgnoreCase(String marca);
}