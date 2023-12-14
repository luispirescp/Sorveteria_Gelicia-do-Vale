package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.domain.dto.ProdutoDTO;
import br.com.cursojava.petshop.domain.produto.Produto;
import br.com.cursojava.petshop.domain.produto.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ProdutoController {
    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;

    }
    @PostMapping(value = {"/create-produto"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> createProduct(@RequestBody ProdutoDTO produtoDTO) throws IOException {
        ProdutoDTO savedProduto = produtoService.save(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    @PostMapping(value = {"/polula-banco"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Produto>> createProductPolulaBanco(@RequestBody List<Produto> produtos) throws IOException {
        List<Produto> savedProduto = produtoService.saveListProdutos(produtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }
    @PostMapping(value = {"/realizar/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> realizarCompra(@RequestParam Long id, @RequestParam int quantity) {
        try {
            produtoService.reduzirQuantidadeDoProduto(id, quantity);
            return ResponseEntity.ok("Compra realizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = {"/reduces-stock"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reducesStock(@RequestBody List<Long> productIds) {
        try {
            produtoService.reduceStock(productIds);
            return ResponseEntity.ok("Redução do estoque com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/todos-produtos-page")
    public ResponseEntity<Page<Produto>> getProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Produto> produtos = produtoService.getProdutoPage(pageable);
        return new ResponseEntity(produtos, HttpStatus.OK);
    }

    @GetMapping("/todos-produtos")
    public ResponseEntity<List<Produto>> getProdutos() {
        List<Produto> produtos = produtoService.getProduto();
        return new ResponseEntity(produtos, HttpStatus.OK);
    }

    @GetMapping(value = {"product/{name}"})
    public ResponseEntity<List<Produto>> getProdutosTOName(@PathVariable String name) {
        List<Produto> produto = this.produtoService.getProdutoTOName(name);
        return new ResponseEntity(produto, HttpStatus.OK);
    }


    @GetMapping({"/quantidade/{name}"})
    public int quantidade(@PathVariable String name) {
        try {

        return produtoService.getQuantityProduto(name);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quantidade Não encontrada").getStatusCodeValue();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        try {
            Optional<Produto> produto = produtoService.getProdutoByID(id);
            return ResponseEntity.ok().body(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("O produto com o ID " + id + " não foi encontrado!");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletarProdutoPorId(id);
            return new ResponseEntity<>("Produto deletado com sucesso", HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O produto com o ID " + id + " não existe!");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            Produto produto = produtoService.alteraProduto(produtoDTO, id);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            String mensagemErro = "Erro ao atualizar o produto com o ID: "+id;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
        }
    }
}
