package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import br.unisul.a3sdm.gestao_estoque_backend.repository.AmigoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
        novoAmigo.setDataCadastro(LocalDateTime.now());
            novoAmigo.setoDevedor(false);
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
                    amigo.setoDevedor(amigoAtualizado.getoDevedor());

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
    
    @GetMapping("/{id}/alerta-devedor")
    public ResponseEntity<Map<String, Object>> verificarAlertaDevedor(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .map(amigo -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("amigoId", amigo.getId());
                    response.put("nome", amigo.getNome());
                    response.put("ehDevedor", amigo.getoDevedor());
                    response.put("mensagem", amigo.getoDevedor() 
                        ? "ALERTA: Este amigo está com pendências!" 
                        : "Amigo em dia com os pagamentos");
                    response.put("dataVerificacao", LocalDateTime.now());
                    
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/devedores")
    public List<Amigo> getAmigosDevedores() {
        return repository.findAll().stream()
                .filter(amigo -> Boolean.TRUE.equals(amigo.getoDevedor()))
                .toList();
    }

    @PatchMapping("/{id}/devedor")
    public ResponseEntity<Amigo> toggleDevedor(@PathVariable @NonNull Long id, 
                                              @RequestParam Boolean devedor) {
        return repository.findById(id)
                .map(amigo -> {
                    amigo.setoDevedor(devedor);
                    return ResponseEntity.ok(repository.save(amigo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

}


