package br.unisul.a3sdm.gestao_estoque_backend.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import br.unisul.a3sdm.gestao_estoque_backend.repository.AmigoRepository;

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
    public ResponseEntity<?> deleteAmigo(@PathVariable @NonNull Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Checa se o amigo tem empréstimos ativos
        if (repository.hasEmprestimosAtivos(id)) {
            // Se possui, retorna um erro 400 (Bad Request) com a mensagem
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Não é possível excluir o amigo, pois ele possui empréstimos ativos. É necessário devolver as ferramentas primeiro.");
            // Retorna o status 400, o Front-end deve capturar e exibir esta mensagem
            return ResponseEntity.badRequest().body(erro);
        }

        // Exclusao: Se não há pendências, exclui o amigo.
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
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


