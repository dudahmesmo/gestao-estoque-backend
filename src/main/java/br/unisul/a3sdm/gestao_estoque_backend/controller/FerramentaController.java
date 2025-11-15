package br.unisul.a3sdm.gestao_estoque_backend.controller;

import br.unisul.a3sdm.gestao_estoque_backend.model.Ferramenta;
import br.unisul.a3sdm.gestao_estoque_backend.repository.FerramentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ferramentas")
public class FerramentaController {

    @Autowired
    private FerramentaRepository ferramentaRepository;

    @GetMapping
    public List<Ferramenta> getAllFerramentas() {
        return ferramentaRepository.findAll();
    }

    @PostMapping
    public Ferramenta createFerramenta(@RequestBody Ferramenta ferramenta) {
        return ferramentaRepository.save(ferramenta);
    }

    @GetMapping("/{id}")
    public Ferramenta getFerramentaById(@PathVariable Long id) {
        return ferramentaRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteFerramenta(@PathVariable Long id) {
        ferramentaRepository.deleteById(id);
    }

    @GetMapping("/disponiveis")
    public List<Ferramenta> getFerramentasDisponiveis() {
        return ferramentaRepository.findByQuantidadeGreaterThan(0);
    }
}
