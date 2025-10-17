package br.unisul.a3sdm.gestao_estoque_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;

@Repository // Avisa ao Spring que esta é uma "ponte" com o banco de dados.
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}