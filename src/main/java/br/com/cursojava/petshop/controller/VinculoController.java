package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.domain.vinculo.VinculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;

    @Autowired
    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @PostMapping("/fornecedor/{fornecedorId}/produto/{produtoId}")
    public ResponseEntity<String> vincularFornecedorProduto(
            @PathVariable Long fornecedorId,
            @PathVariable Long produtoId) {
        vinculoService.vincularFornecedorProduto(fornecedorId, produtoId);
        return ResponseEntity.ok("Vínculo criado com sucesso!");
    }
}

