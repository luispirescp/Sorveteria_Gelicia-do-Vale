package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.model.Tipo;
import br.com.cursojava.petshop.service.ControleEstoqueService;
import br.com.cursojava.petshop.service.ProdutoService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final ControleEstoqueService controleEstoqueService;
    public ProdutoController(ProdutoService produtoService, ControleEstoqueService controleEstoqueService) {
        this.produtoService = produtoService;
        this.controleEstoqueService = controleEstoqueService;
    }
    @PostMapping(value = {"/create-produto"},consumes = {"multipart/form-data"})
    public ResponseEntity<Produto> criaProduto(@RequestParam String name, @RequestParam String tipo, @RequestParam String description, @RequestParam String barcode, @RequestParam double price, @RequestParam int quantity, @RequestPart MultipartFile image) throws IOException {
            Produto produto = new Produto();
            String nomeImagemHash = produtoService.saveImage(image);
            produto.setName(name);
            produto.setTipo(tipo);
            produto.setDescription(description);
            produto.setBarcode(barcode);
            produto.setPrice(price);
            produto.setQuantity(quantity);
            produto.setImage(nomeImagemHash);
            produto = this.produtoService.criarProduto(produto);
            return new ResponseEntity(produto, HttpStatus.CREATED);
    }

    @GetMapping({"/todos-produtos"})
    public ResponseEntity<List<Produto>> getTodosProdutos() {
        List<Produto> produto = this.produtoService.getProduto();
        return new ResponseEntity(produto, HttpStatus.OK);
    }

    @GetMapping({"/file/{name}"})
    public  ResponseEntity<Object> getImage(@PathVariable String name) throws IOException {
        String baseUrlImage = "http://localhost:8080/public/";
        String imageUrl = baseUrlImage+produtoService.getImageProduto(name);
        return ResponseEntity.status(302).location(java.net.URI.create(imageUrl)).build();
}

    @GetMapping({"/quantidade/{name}"})
    public int quantidade(@PathVariable String name){
    return produtoService.getQuantityProduto(name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProdutoPorId(id);
        return new ResponseEntity<>("Produto deletado com sucesso", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProdito(@PathVariable Long id, @RequestBody Produto produto){
        produtoService.alteraProduto(produto,id);
        return new ResponseEntity<>("Produto Atualizado",HttpStatus.OK);
    }
}
