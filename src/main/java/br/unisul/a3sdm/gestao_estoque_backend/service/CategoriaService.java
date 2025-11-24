package br.unisul.a3sdm.gestao_estoque_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;
import br.unisul.a3sdm.gestao_estoque_backend.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Optional<Categoria> findById(Long id) {
        return repository.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
