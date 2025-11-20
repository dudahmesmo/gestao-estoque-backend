package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ferramentas")
public class FerramentaController {

    @Autowired
    private FerramentaRepository ferramentaRepository;

    // CADASTRAR
    @PostMapping
    public ResponseEntity<?> createFerramenta(@RequestBody Ferramenta ferramenta) {
        try {

            if (ferramenta.getPreco() != null && ferramenta.getPreco() < 0) {
                return ResponseEntity.badRequest().body("ALERTA: Não é permitido preço negativo.");
            }

            if (ferramenta.getNome() == null || ferramenta.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O campo 'nome' é obrigatório.");
            }

            Ferramenta savedFerramenta = ferramentaRepository.save(ferramenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFerramenta);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar ferramenta: " + e.getMessage());
        }
    }

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Ferramenta>> getAllFerramentas() {
        return ResponseEntity.ok(ferramentaRepository.findAll());
    }

    // LISTAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Ferramenta> getFerramentaById(@PathVariable Long id) {
        Optional<Ferramenta> ferramenta = ferramentaRepository.findById(id);
        return ferramenta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // EXCLUIR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFerramenta(@PathVariable Long id) {
        if (!ferramentaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        ferramentaRepository.deleteById(id);
        return ResponseEntity.ok("Ferramenta excluída com sucesso!");
    }

    // DISPONÍVEIS
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Ferramenta>> getFerramentasDisponiveis() {
        return ResponseEntity.ok(ferramentaRepository.findByDisponivelTrue());
    }

    // ALERTA DE PREÇO NEGATIVO
    @GetMapping("/alerta-devedores")
    public ResponseEntity<List<Ferramenta>> getFerramentasComAlertaDevedor() {
        return ResponseEntity.ok(ferramentaRepository.findByPrecoLessThan(0.0));
    }
}
