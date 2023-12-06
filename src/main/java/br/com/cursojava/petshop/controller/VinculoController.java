package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.domain.fornecedor.Fornecedor;
import br.com.cursojava.petshop.domain.produto.Produto;
import br.com.cursojava.petshop.domain.vinculo.VinculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class VinculoController {

    private final VinculoService vinculoService;

    @Autowired
    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }
    @PostMapping(value = {"/vinculo-fornecedor-produto"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void vincularFornecedorProduto(@RequestBody Object dados) {

        try {
                vinculoService.vinculoForncedorProduto((Map<String, List<Map<String, Object>>>) dados);

        }catch (RuntimeException e){
            throw new RuntimeException("Erro ao tentar salvar os dados ");
        }
    }
}

