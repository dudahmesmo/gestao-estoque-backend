package br.unisul.a3sdm.gestao_estoque_backend.service;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map;

@Service
public class FerramentaService {

    private final FerramentaRepository repository;

    public FerramentaService(FerramentaRepository repository) {
        this.repository = repository;
    }

    /**
     * Diminui a quantidade em estoque de uma ferramenta ao registrar um empréstimo.
     * @param id 
     * @throws RuntimeException se o estoque estiver zerado.
     */
    @Transactional
    public void diminuirEstoque(Long id) {
        Optional<Ferramenta> optionalFerramenta = repository.findById(id);

        if (optionalFerramenta.isEmpty()) {
            throw new RuntimeException("Falha na operação: Ferramenta não encontrada com ID: " + id);
        }

        Ferramenta ferramenta = optionalFerramenta.get();

        // 1. Verificação de estoque mínimo.
        if (ferramenta.getQuantidadeEstoque() <= 0) {
            throw new RuntimeException("Falha ao registrar empréstimo: A ferramenta está fora de estoque.");
        }

        // 2. Diminui a quantidade e salva no bd.
        int novoEstoque = ferramenta.getQuantidadeEstoque() - 1;
        ferramenta.setQuantidadeEstoque(novoEstoque);
        
        repository.save(ferramenta);
    }
    
    /**
     * Aumenta a quantidade em estoque de uma ferramenta ao registrar uma devolução.
     * @param id 
     * @throws RuntimeException se o estoque exceder o máximo permitido.
     */
    @Transactional
    public void aumentarEstoque(Long id) {
        Optional<Ferramenta> optionalFerramenta = repository.findById(id);

        if (optionalFerramenta.isEmpty()) {
            throw new RuntimeException("Falha na operação: Ferramenta não encontrada com ID: " + id);
        }

        Ferramenta ferramenta = optionalFerramenta.get();

        // 1. Definição do Limite Máximo
        int estoqueAtual = ferramenta.getQuantidadeEstoque();
        int quantidadeMaxima = ferramenta.getQuantidadeMaxima() > 0 ? ferramenta.getQuantidadeMaxima() : 15;
        

        // 2. Verificação de estoque máximo
        if (estoqueAtual >= quantidadeMaxima) {
            throw new RuntimeException("Falha ao registrar devolução: O estoque máximo (" + quantidadeMaxima + ") para esta ferramenta foi atingido.");
        }

        // 3. Aumenta a quantidade e salva no bd.
        int novoEstoque = estoqueAtual + 1;
        ferramenta.setQuantidadeEstoque(novoEstoque);
        
        repository.save(ferramenta);
    }


    /**
     * Implementa a lógica que verifica e retorna o status de alerta da ferramenta.
     * @param ferramenta 
     * @return 
     */
    public String verificarAlertaEstoque(Ferramenta ferramenta) {
        int estoqueAtual = ferramenta.getQuantidadeEstoque();
        int quantidadeMinima = ferramenta.getQuantidadeMinima() > 0 ? ferramenta.getQuantidadeMinima() : 3;
        int quantidadeMaxima = ferramenta.getQuantidadeMaxima() > 0 ? ferramenta.getQuantidadeMaxima() : 15;
        

        // REGRA DE ALERTA 1: Estoque Mínimo (Necessidade de Compra)
        if (estoqueAtual <= quantidadeMinima) {
            return "ALERTA MÍNIMO: Estoque baixo (" + estoqueAtual + "). Necessário reabastecer!";
        }
        
        // REGRA DE ALERTA 2: Estoque Máximo (Usado para checar se está muito cheio)
        if (estoqueAtual > quantidadeMaxima) {
             return "ALERTA MÁXIMO: Estoque acima do limite permitido (" + quantidadeMaxima + ").";
        }
        
        return "Estoque OK";
    }

    
    // MÉTODO PARA RELATÓRIO DE CUSTO TOTAL 

    /**
     * Método que calcula o custo total por ferramenta e o custo total geral do estoque.
     * Usado para o Relatório de Ferramentas e Custos.
     * @return Map<String, Object> contendo a lista detalhada e o total geral.
     */
    public Map<String, Object> calcularCustoTotalEstoque() {
        
        // 1. Buscar todas as ferramentas
        List<Ferramenta> todasFerramentas = repository.findAll();
        double custoTotalGeral = 0.0;
        
        // 2. Processar cada ferramenta e calcular seu custo total individual
        List<Map<String, Object>> detalhes = new ArrayList<>();

        for (Ferramenta f : todasFerramentas) {
            // Garante que o valor não é null
            int quantidade = f.getQuantidadeEstoque() != null ? f.getQuantidadeEstoque() : 0;
            double custoUnitario = f.getPreco() != null ? f.getPreco() : 0.0;
            
            // CÁLCULO CRÍTICO: Custo Total = Custo Unitário x Qtd. em Estoque
            double custoTotalFerramenta = custoUnitario * quantidade;
            
            // Acumular o total geral
            custoTotalGeral += custoTotalFerramenta;
            
            
            Map<String, Object> detalhe = new HashMap<>();
            detalhe.put("id", f.getId());
            detalhe.put("nome", f.getNome());
            detalhe.put("quantidade_em_estoque", quantidade);
            detalhe.put("custo_unitario", custoUnitario);
            detalhe.put("custo_total_ferramenta", custoTotalFerramenta);
            
            detalhes.add(detalhe);
        }
        
        // 3. Construir a resposta final
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("detalhes_ferramentas", detalhes);
        resultado.put("custo_total_geral", custoTotalGeral);
        
        return resultado;
    }
    
        public List<Ferramenta> findByCategoriaId(Long categoriaId) {
            return repository.findByCategoriaId(categoriaId);
        }

        public List<Ferramenta> findSemCategoria() {
            return repository.findByCategoriaIsNull();
        }
}
