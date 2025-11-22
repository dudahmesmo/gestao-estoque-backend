package br.unisul.a3sdm.gestao_estoque_backend.service;

import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;
import br.unisul.a3sdm.gestao_estoque_backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAllByOrderByNomeAsc();
    }

    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Optional<Categoria> findByNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    public boolean existsByNome(String nome) {
        return categoriaRepository.existsByNome(nome);
    }

    // Método para obter apenas os nomes das categorias (para o comboBox)
    public List<String> findAllNomes() {
        return categoriaRepository.findAllByOrderByNomeAsc()
                .stream()
                .map(Categoria::getNome)
                .toList();
    }
}
