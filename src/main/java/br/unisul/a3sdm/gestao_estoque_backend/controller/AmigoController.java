package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import br.unisul.a3sdm.gestao_estoque_backend.repository.AmigoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amigos")
@CrossOrigin(origins = "*")
public class AmigoController {

    private final AmigoRepository repository;

    public AmigoController(AmigoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Amigo> getAllAmigos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Amigo> getAmigoById(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Amigo> createAmigo(@RequestBody @NonNull Amigo novoAmigo) {
        Amigo salvo = repository.save(novoAmigo);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Amigo> updateAmigo(@PathVariable @NonNull Long id,
                                             @RequestBody @NonNull Amigo amigoAtualizado) {
        return repository.findById(id)
                .map(amigo -> {
                    // atualiza apenas campos não nulos (evita sobrescrever com null)
                    if (amigoAtualizado.getNome() != null) {
                        amigo.setNome(amigoAtualizado.getNome());
                    }
                    if (amigoAtualizado.getTelefone() != null) {
                        amigo.setTelefone(amigoAtualizado.getTelefone());
                    }
                    if (amigoAtualizado.getEmail() != null) {
                        amigo.setEmail(amigoAtualizado.getEmail());
                    }
                   return ResponseEntity.ok(repository.save(amigo));

                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmigo(@PathVariable @NonNull Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


