package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.model.Categoria;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import br.unisul.a3sdm.gestao_estoque_backend.service.CategoriaService;
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
    private final CategoriaService categoriaService;

    public FerramentaController(FerramentaRepository ferramentaRepository,
                                FerramentaService ferramentaService,
                                CategoriaService categoriaService) {
        this.ferramentaRepository = ferramentaRepository;
        this.ferramentaService = ferramentaService;
        this.categoriaService = categoriaService;
    }

    // CADASTRAR (ATUALIZADO COM VALIDAÇÃO DE CATEGORIA)
    @PostMapping
    public ResponseEntity<?> createFerramenta(@RequestBody Ferramenta ferramenta) {
        try {
            // Validação do nome
            if (ferramenta.getNome() == null || ferramenta.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O campo 'nome' é obrigatório.");
            }

            // Validação do preço
            if (ferramenta.getPreco() != null && ferramenta.getPreco() < 0) {
                return ResponseEntity.badRequest().body("ALERTA: Não é permitido preço negativo.");
            }

            // Validação da categoria (se foi informada)
            if (ferramenta.getCategoria() != null && ferramenta.getCategoria().getId() != null) {
                Optional<Categoria> categoriaExistente = categoriaService.findById(ferramenta.getCategoria().getId());
                if (categoriaExistente.isEmpty()) {
                    return ResponseEntity.badRequest().body("Categoria não encontrada com ID: " + ferramenta.getCategoria().getId());
                }
                // Garante que a categoria tenha todos os dados corretos
                ferramenta.setCategoria(categoriaExistente.get());
            }

            Ferramenta savedFerramenta = ferramentaRepository.save(ferramenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFerramenta);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar ferramenta: " + e.getMessage());
        }
    }

    // ATUALIZAR FERRAMENTA (NOVO ENDPOINT - IMPORTANTE PARA EDITAR CATEGORIA)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFerramenta(@PathVariable Long id, @RequestBody Ferramenta ferramentaAtualizada) {
        try {
            Optional<Ferramenta> ferramentaExistente = ferramentaRepository.findById(id);
            if (ferramentaExistente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Ferramenta ferramenta = ferramentaExistente.get();

            // Atualiza os campos básicos
            if (ferramentaAtualizada.getNome() != null) {
                ferramenta.setNome(ferramentaAtualizada.getNome());
            }
            if (ferramentaAtualizada.getMarca() != null) {
                ferramenta.setMarca(ferramentaAtualizada.getMarca());
            }
            if (ferramentaAtualizada.getPreco() != null) {
                if (ferramentaAtualizada.getPreco() < 0) {
                    return ResponseEntity.badRequest().body("ALERTA: Não é permitido preço negativo.");
                }
                ferramenta.setPreco(ferramentaAtualizada.getPreco());
            }
            if (ferramentaAtualizada.getQuantidadeEstoque() != null) {
                ferramenta.setQuantidadeEstoque(ferramentaAtualizada.getQuantidadeEstoque());
            }
            if (ferramentaAtualizada.getQuantidadeMinimaEstoque() != null) {
                ferramenta.setQuantidadeMinimaEstoque(ferramentaAtualizada.getQuantidadeMinimaEstoque());
            }
            if (ferramentaAtualizada.getQuantidadeMaximaEstoque() != null) {
                ferramenta.setQuantidadeMaximaEstoque(ferramentaAtualizada.getQuantidadeMaximaEstoque());
            }
            if (ferramentaAtualizada.getDisponivel() != null) {
                ferramenta.setDisponivel(ferramentaAtualizada.getDisponivel());
            }

            // Validação e atualização da categoria
            if (ferramentaAtualizada.getCategoria() != null) {
                if (ferramentaAtualizada.getCategoria().getId() != null) {
                    Optional<Categoria> categoriaExistente = categoriaService.findById(ferramentaAtualizada.getCategoria().getId());
                    if (categoriaExistente.isEmpty()) {
                        return ResponseEntity.badRequest().body("Categoria não encontrada com ID: " + ferramentaAtualizada.getCategoria().getId());
                    }
                    ferramenta.setCategoria(categoriaExistente.get());
                } else {
                    // Se a categoria não tem ID, remove a categoria da ferramenta
                    ferramenta.setCategoria(null);
                }
            }

            Ferramenta ferramentaSalva = ferramentaRepository.save(ferramenta);
            return ResponseEntity.ok(ferramentaSalva);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar ferramenta: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Ferramenta>> getAllFerramentas() {
        return ResponseEntity.ok(ferramentaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ferramenta> getFerramentaById(@PathVariable Long id) {
        Optional<Ferramenta> ferramenta = ferramentaRepository.findById(id);
        return ferramenta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFerramenta(@PathVariable Long id) {
        if (!ferramentaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        ferramentaRepository.deleteById(id);
        return ResponseEntity.ok("Ferramenta excluída com sucesso!");
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Ferramenta>> getFerramentasDisponiveis() {
        return ResponseEntity.ok(ferramentaRepository.findByDisponivelTrue());
    }

    @GetMapping("/alerta-devedores")
    public ResponseEntity<List<Ferramenta>> getFerramentasComAlertaDevedor() {
        return ResponseEntity.ok(ferramentaRepository.findByPrecoLessThan(0.0));
    }

    @GetMapping("/relatorios/custo-total")
    public ResponseEntity<Map<String, Object>> getCustoTotalEstoque() {
        try {
            Map<String, Object> resultado = ferramentaService.calcularCustoTotalEstoque();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("mensagem", "Erro ao gerar relatório de custos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }
}
