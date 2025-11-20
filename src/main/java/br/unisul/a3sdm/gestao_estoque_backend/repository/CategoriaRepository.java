package br.unisul.a3sdm.gestao_estoque_backend.repository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}

