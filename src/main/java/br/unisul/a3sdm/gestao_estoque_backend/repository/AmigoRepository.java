package br.unisul.a3sdm.gestao_estoque_backend.repository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmigoRepository extends JpaRepository<Amigo, Long> {
}
