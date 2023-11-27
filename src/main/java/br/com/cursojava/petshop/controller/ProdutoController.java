package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.enums.Tipow;
import br.com.cursojava.petshop.exception.TipoNaoEncontradoException;
import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.model.Tipo;
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

    @PostMapping(value = {"/create-produto"},
            consumes = {"multipart/form-data"})
    public ResponseEntity<Produto> criaProduto(@RequestParam String name, @RequestParam Tipo tipo, @RequestParam String description, @RequestParam String barcode, @RequestParam double price, @RequestParam int quantity, @RequestPart MultipartFile image) {
        try {
            Produto produto = new Produto();
            Tipo.fromString(produto.getTipo().toString());
            produto.setName(name);
            produto.setTipo(tipo);
            produto.setDescription(description);
            produto.setBarcode(barcode);
            produto.setPrice(price);
            produto.setQuantity(quantity);
            produto.setImage(image.getBytes());
            produto = this.produtoService.criarProduto(produto);
            return new ResponseEntity(produto, HttpStatus.CREATED);
        } catch (TipoNaoEncontradoException | IOException e) {
            System.out.println("Exceção capturada: " + e.getMessage());


        }

        return null;
    }




    @GetMapping({"/todos-produtos"})
    public ResponseEntity<List<Produto>> getTodosProdutos() {
        List<Produto> produto = this.produtoService.getProduto();
        return new ResponseEntity(produto, HttpStatus.OK);
    }

    @GetMapping({"/produto/{name}"})
    public ResponseEntity<List<Produto>> getAnimalPorNome(@PathVariable String name) {
        return new ResponseEntity(this.produtoService.getProdutoPorName(name), HttpStatus.OK);
    }
}
