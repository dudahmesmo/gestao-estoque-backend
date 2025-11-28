package br.unisul.a3sdm.gestao_estoque_backend.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.unisul.a3sdm.gestao_estoque_backend.model.Amigo;
import br.unisul.a3sdm.gestao_estoque_backend.model.Emprestimo;
import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.repository.AmigoRepository;
import br.unisul.a3sdm.gestao_estoque_backend.repository.EmprestimoRepository;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import br.unisul.a3sdm.gestao_estoque_backend.service.FerramentaService;

@RestController
@RequestMapping("/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoRepository repository;
    private final AmigoRepository amigoRepository;
    private final FerramentaRepository ferramentaRepository;
    private final FerramentaService ferramentaService;

    public EmprestimoController(EmprestimoRepository repository,
            AmigoRepository amigoRepository,
            FerramentaRepository ferramentaRepository,
            FerramentaService ferramentaService) {
        this.repository = repository;
        this.amigoRepository = amigoRepository;
        this.ferramentaRepository = ferramentaRepository;
        this.ferramentaService = ferramentaService;
    }

    private Map<String, Object> mapEmprestimoToFlatDto(Emprestimo e) {
        Map<String, Object> item = new HashMap<>();

        // 1. Mapeamento dos campos de empréstimo 
        item.put("idEmprestimo", e.getId());
        item.put("dataEmprestimo", e.getDataEmprestimo());
        item.put("dataDevolucaoEsperada", e.getDataDevolucao());
        item.put("statusEmprestimo", e.isAtivo() ? "Em dia" : "Devolvido");
        item.put("ativo", e.isAtivo());

        if (e.getAmigo() != null) {
            item.put("idAmigo", e.getAmigo().getId());
            item.put("amigoNome", e.getAmigo().getNome());
            item.put("telefoneUsuario", e.getAmigo().getTelefone());
        } else {
            item.put("idAmigo", 0);
            item.put("amigoNome", "Amigo Não Encontrado");
            item.put("telefoneUsuario", "N/A");
        }

        if (e.getFerramenta() != null) {
            item.put("idFerramenta", e.getFerramenta().getId());
            item.put("ferramentaNome", e.getFerramenta().getNome());
            item.put("marcaFerramenta", e.getFerramenta().getMarca());
            item.put("preco", e.getFerramenta().getPreco());
        } else {
            item.put("idFerramenta", 0);
            item.put("ferramentaNome", "Ferramenta Não Encontrada");
            item.put("marcaFerramenta", "N/A");
            item.put("preco", 0.0);
        }

        return item;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return repository.findAll().stream()
                .map(this::mapEmprestimoToFlatDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/historico")
    public List<Map<String, Object>> getHistorico() {
        return repository.findAll().stream()
                .map(this::mapEmprestimoToFlatDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/ativos")
    public List<Map<String, Object>> getAtivos() {
        return repository.findByAtivoTrue().stream()
                .map(this::mapEmprestimoToFlatDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> getById(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @NonNull Emprestimo novo) {
        try {
            // Data automática
            if (novo.getDataEmprestimo() == null) {
                novo.setDataEmprestimo(LocalDate.now());
            }

            // Verificar se o amigo existe
            if (novo.getAmigo() == null || novo.getAmigo().getId() == null) {
                return ResponseEntity.badRequest().body("Amigo é obrigatório");
            }

            Optional<Amigo> amigoOpt = amigoRepository.findById(novo.getAmigo().getId());
            if (amigoOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Amigo não encontrado");
            }

            // Verificar se a ferramenta existe
            if (novo.getFerramenta() == null || novo.getFerramenta().getId() == null) {
                return ResponseEntity.badRequest().body("Ferramenta é obrigatória");
            }

            Optional<Ferramenta> ferramentaOpt = ferramentaRepository.findById(novo.getFerramenta().getId());
            if (ferramentaOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Ferramenta não encontrada");
            }

            // LÓGICA DE ESTOQUE (Saída/Empréstimo)
            ferramentaService.diminuirEstoque(novo.getFerramenta().getId());

            // Busca objeto atualizado
            Ferramenta ferramentaAtualizada = ferramentaRepository.findById(novo.getFerramenta().getId()).get();

            // O serviço verifica o alerta
            String alertaEstoque = ferramentaService.verificarAlertaEstoque(ferramentaAtualizada);

            // Alerta de devedor - verificar se amigo tem empréstimos em atraso
            List<Emprestimo> emprestimosAtraso = repository.findByAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(
                    LocalDate.now());

            if (!emprestimosAtraso.isEmpty()) {
                System.out.println("ALERTA: Amigo " + amigoOpt.get().getNome()
                        + " possui " + emprestimosAtraso.size() + " empréstimos em atraso!");
            }

            novo.setAtivo(true);

            // Salvar o empréstimo
            Emprestimo salvo = repository.save(novo);

            Map<String, Object> response = new HashMap<>();
            response.put("emprestimo", salvo);
            response.put("alerta_estoque", alertaEstoque); // Adiciona o alerta à resposta

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Captura a exceção lançada pelo FerramentaService (Ex: "Ferramenta está fora de estoque.")
            return ResponseEntity.badRequest().body(e.getMessage());

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

    @GetMapping("/atraso")
    public List<Emprestimo> getEmAtraso() {
        return repository.findByAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(LocalDate.now());
    }

    @PutMapping("/{id}/devolver")
    @Transactional
    public ResponseEntity<?> devolver(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .map(e -> {
                    try {
                        // 1. Aumentar estoque
                        ferramentaService.aumentarEstoque(e.getFerramenta().getId());

                        // 2. Marcar como devolvido
                        e.setDataDevolucao(LocalDate.now());
                        e.setAtivo(false);
                        Emprestimo devolvido = repository.save(e);

                        // 3. Checar alerta
                        Ferramenta ferramentaAtualizada = ferramentaRepository.findById(e.getFerramenta().getId()).get();
                        String alertaEstoque = ferramentaService.verificarAlertaEstoque(ferramentaAtualizada);

                        Map<String, Object> response = new HashMap<>();
                        response.put("emprestimo", devolvido);
                        response.put("alerta_estoque", alertaEstoque);

                        return ResponseEntity.ok(response);

                    } catch (RuntimeException re) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/relatorios/mais-emprestadas")
    public ResponseEntity<List<Object[]>> getFerramentasMaisEmprestadas() {
        List<Object[]> resultados = repository.findFerramentaMaisEmprestada();

        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/relatorios/mais-devolvidas")
    public ResponseEntity<List<Object[]>> getFerramentasMaisDevolvidas() {
        List<Object[]> resultados = repository.findFerramentaMaisDevolvida();

        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultados);
    }

    // Retorna uma lista de amigos que têm empréstimos em atraso (devedores).
    @GetMapping("/relatorios/devedores-em-atraso")
    public List<Amigo> getDevedoresEmAtraso() {

        try {
            List<Emprestimo> emprestimosEmAtraso = repository.findByAtivoTrueAndDataDevolucaoIsNullOrDataDevolucaoBefore(LocalDate.now());

            if (emprestimosEmAtraso.isEmpty()) {
                return java.util.Collections.emptyList();
            }

            return emprestimosEmAtraso.stream()
                    .map(Emprestimo::getAmigo)
                    .distinct()
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // 3. Se houver um erro de execução (JPQL/SQL quebrado), o erro é lançado como 500
            System.err.println("ERRO CRÍTICO NO REPOSITÓRIO: Falha ao buscar devedores. Detalhe: " + e.getMessage());
            e.printStackTrace();

            // Lança uma exceção HTTP 500 para que o Frontend saiba que a API falhou.
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "A consulta de DEVEDORES no Repositório falhou. Verificar log do Backend.");
        }
    }
}
