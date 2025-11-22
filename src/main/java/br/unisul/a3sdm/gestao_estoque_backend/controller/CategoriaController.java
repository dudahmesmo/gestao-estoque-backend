package br.unisul.a3sdm.gestao_estoque_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;
import br.unisul.a3sdm.gestao_estoque_backend.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*") 
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    // 1. CADASTRAR (POST)
    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody @NonNull Categoria novaCategoria) {
        if (novaCategoria.getNome() == null || novaCategoria.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Categoria salva = repository.save(novaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // 2. LISTAR TODOS (GET)
    @GetMapping
    public List<Categoria> getAllCategorias() {
        return repository.findAll();
    }

    // 3. BUSCAR POR ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. ATUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable @NonNull Long id, 
                                                    @RequestBody @NonNull Categoria categoriaAtualizada) {
        return repository.findById(id)
                .map(categoria -> {
                    if (categoriaAtualizada.getNome() != null) {
                        categoria.setNome(categoriaAtualizada.getNome());
                    }
                    return ResponseEntity.ok(repository.save(categoria));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. EXCLUIR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable @NonNull Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}