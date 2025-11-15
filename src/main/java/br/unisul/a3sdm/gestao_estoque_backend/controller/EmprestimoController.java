package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Emprestimo;
import br.unisul.a3sdm.gestao_estoque_backend.repository.EmprestimoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoRepository repository;

    public EmprestimoController(EmprestimoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Emprestimo> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> getById(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Emprestimo> create(@RequestBody @NonNull Emprestimo novo) {
        Emprestimo salvo = repository.save(novo);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> update(@PathVariable @NonNull Long id,
                                             @RequestBody @NonNull Emprestimo atualizado) {
        return repository.findById(id)
                .map(e -> {
                    if (atualizado.getDataEmprestimo() != null) {
                        e.setDataEmprestimo(atualizado.getDataEmprestimo());
                    }
                    if (atualizado.getDataDevolucao() != null) {
                        e.setDataDevolucao(atualizado.getDataDevolucao());
                    }
                    // atualizar chaves estrangeiras apenas se necessário
                    return ResponseEntity.ok(repository.save(e));

                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}