package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Emprestimo;
import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.repository.EmprestimoRepository;
import br.unisul.a3sdm.gestao_estoque_backend.repository.AmigoRepository;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoRepository repository;
    private final AmigoRepository amigoRepository;
    private final FerramentaRepository ferramentaRepository;

    public EmprestimoController(EmprestimoRepository repository, 
                              AmigoRepository amigoRepository,
                              FerramentaRepository ferramentaRepository) {
        this.repository = repository;
        this.amigoRepository = amigoRepository;
        this.ferramentaRepository = ferramentaRepository;
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
    public ResponseEntity<?> create(@RequestBody @NonNull Emprestimo novo) {
        try {
            // Lógica 1: Data automática - definir data do empréstimo como data atual
            if (novo.getDataEmprestimo() == null) {
                novo.setDataEmprestimo(LocalDate.now());
            }
            
            // Lógica 2: Verificar se o amigo existe
            if (novo.getAmigo() == null || novo.getAmigo().getId() == null) {
                return ResponseEntity.badRequest().body("Amigo é obrigatório");
            }
            
            Optional<Amigo> amigoOpt = amigoRepository.findById(novo.getAmigo().getId());
            if (amigoOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Amigo não encontrado");
            }
            
            // Lógica 3: Verificar se a ferramenta existe
            if (novo.getFerramenta() == null || novo.getFerramenta().getId() == null) {
                return ResponseEntity.badRequest().body("Ferramenta é obrigatória");
            }
            
            Optional<Ferramenta> ferramentaOpt = ferramentaRepository.findById(novo.getFerramenta().getId());
            if (ferramentaOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Ferramenta não encontrada");
            }
            
            // Lógica 4: Alerta de devedor - verificar se amigo tem empréstimos em atraso
            List<Emprestimo> emprestimosAtraso = repository.findByAmigoAndAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(
                amigoOpt.get(), LocalDate.now());
            
            if (!emprestimosAtraso.isEmpty()) {
                System.out.println("ALERTA: Amigo " + amigoOpt.get().getNome() + 
                                 " possui " + emprestimosAtraso.size() + " empréstimos em atraso!");
            }
            novo.setAtivo(true);
            
            // Salvar o empréstimo
            Emprestimo salvo = repository.save(novo);
            return ResponseEntity.ok(salvo);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar empréstimo: " + e.getMessage());
        }
    }

    // Endpoint para atualizar empréstimo
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
                        e.setAtivo(false);
                    }
                    if (atualizado.getAmigo() != null) {
                        e.setAmigo(atualizado.getAmigo());
                    }
                    if (atualizado.getFerramenta() != null) {
                        e.setFerramenta(atualizado.getFerramenta());
                    }
                    e.setAtivo(atualizado.isAtivo());
                    
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

    @GetMapping("/ativos")
    public List<Emprestimo> getAtivos() {
        return repository.findByAtivoTrue();
    }

    @GetMapping("/atraso")
    public List<Emprestimo> getEmAtraso() {
        return repository.findByAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(LocalDate.now());
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Emprestimo> devolver(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .map(e -> {
                    e.setDataDevolucao(LocalDate.now());
                    e.setAtivo(false);
                    return ResponseEntity.ok(repository.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}