package br.unisul.a3sdm.gestao_estoque_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;
import br.unisul.a3sdm.gestao_estoque_backend.repository.CategoriaRepository;

@RestController 
@RequestMapping("/api/categorias") 
public class CategoriaController {

   
    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Endpoint para listar todas as categorias.
     * Mapeado para o método GET em /api/categorias
     */
    @GetMapping
    public List<Categoria> listarTodas() {
        
        return categoriaRepository.findAll();
    }

    /**
     * Endpoint para criar uma nova categoria.
     * Mapeado para o método POST em /api/categorias
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna o código HTTP 201 (Created)
    public Categoria criarCategoria(@RequestBody Categoria categoria) {
        
        return categoriaRepository.save(categoria);
    }
}