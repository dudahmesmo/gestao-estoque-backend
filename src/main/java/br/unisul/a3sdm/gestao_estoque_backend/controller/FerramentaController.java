package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import br.unisul.a3sdm.gestao_estoque_backend.service.FerramentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ferramentas")
public class FerramentaController {

    private final FerramentaRepository ferramentaRepository;
    private final FerramentaService ferramentaService;

    public FerramentaController(FerramentaRepository ferramentaRepository,
                                FerramentaService ferramentaService) {
        this.ferramentaRepository = ferramentaRepository;
        this.ferramentaService = ferramentaService;
    }

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
    

    // ENDPOINT: RELATÓRIO DE CUSTO TOTAL

    /**
     * Endpoint para o Relatório de Ferramentas e Custos.
     * Chama o serviço para calcular o custo total de estoque.
     */
    @GetMapping("/relatorios/custo-total")
    public ResponseEntity<Map<String, Object>> getCustoTotalEstoque() {
        try {
            Map<String, Object> resultado = ferramentaService.calcularCustoTotalEstoque();
            
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            // Em caso de falha, retorna um erro interno 500
            Map<String, Object> erro = new HashMap<>();
            erro.put("mensagem", "Erro ao gerar relatório de custos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }
}
