package br.unisul.a3sdm.gestao_estoque_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria; 

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    Optional<Categoria> findByNome(String nome); 
}