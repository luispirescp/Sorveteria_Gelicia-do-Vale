package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    //    Cria Produto
    @PostMapping(value = "/create-produto", consumes = "multipart/form-data")
    public ResponseEntity<Produto> criaProduto(@RequestParam String name, @RequestParam String description, @RequestParam String barcode, @RequestParam double price, @RequestParam int quantity, @RequestPart MultipartFile image) throws IOException {
        Produto produto = new Produto();
        produto.setName(name);
        produto.setDescription(description);
        produto.setBarcode(barcode);
        produto.setPrice(price);
        produto.setQuantity(quantity);
        produto.setImage(image.getBytes());
        produto = produtoService.criarProduto(produto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @GetMapping("/todos-produtos")
    public ResponseEntity<List<Produto>> getTodosProdutos() {
        List<Produto> produto = produtoService.getProduto();
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    //    Buscar por nome
    @GetMapping("/produto/{name}")
    public ResponseEntity<List<Produto>> getAnimalPorNome(@PathVariable String name) {
        return new ResponseEntity<>(produtoService.getProdutoPorName(name), HttpStatus.OK);
    }
}
