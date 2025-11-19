package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ferramentas")
public class FerramentaController {

    @Autowired
    private FerramentaRepository ferramentaRepository;

// CADASTRAR - Com data automática e validação de devedor
    @PostMapping
    public ResponseEntity<?> createFerramenta(@RequestBody Ferramenta ferramenta) {
        try {
            
            if (ferramenta.getPreco() < 0) {
                return ResponseEntity.badRequest().body("ALERTA: Não é permitido cadastrar ferramentas com preço negativo.");
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
        try {
            List<Ferramenta> ferramentas = ferramentaRepository.findAll();
            return ResponseEntity.ok(ferramentas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // LISTAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Ferramenta> getFerramentaById(@PathVariable Long id) {
        try {
            Optional<Ferramenta> ferramenta = ferramentaRepository.findById(id);
            return ferramenta.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // EXCLUIR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFerramenta(@PathVariable Long id) {
        try {
            if (!ferramentaRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            ferramentaRepository.deleteById(id);
            return ResponseEntity.ok("Ferramenta excluída com sucesso!");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir ferramenta: " + e.getMessage());
        }
    }

    // LISTAR DISPONÍVEIS
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Ferramenta>> getFerramentasDisponiveis() {
        try {
            List<Ferramenta> disponiveis = ferramentaRepository.findByDisponivelTrue();
            return ResponseEntity.ok(disponiveis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // NOVO ENDPOINT: Listar ferramentas com alerta de devedor (preço negativo)
    @GetMapping("/alerta-devedores")
    public ResponseEntity<List<Ferramenta>> getFerramentasComAlertaDevedor() {
        try {
            List<Ferramenta> devedores = ferramentaRepository.findByPrecoLessThan(0.0);
            return ResponseEntity.ok(devedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}