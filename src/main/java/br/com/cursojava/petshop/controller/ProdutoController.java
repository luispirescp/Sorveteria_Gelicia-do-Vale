package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.dto.ProdutoDTO;
import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.service.ControleEstoqueService;
import br.com.cursojava.petshop.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final ControleEstoqueService controleEstoqueService;
    public ProdutoController(ProdutoService produtoService, ControleEstoqueService controleEstoqueService) {
        this.produtoService = produtoService;
        this.controleEstoqueService = controleEstoqueService;
    }
    @PostMapping(value = {"/create-produto"},consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoDTO> createProduct(@RequestBody ProdutoDTO produtoDTO) throws IOException {
            ProdutoDTO savedProduto = produtoService.save(produtoDTO);
            return  ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }
    @PostMapping(value = {"/save-image/{id}"},consumes = {"multipart/form-data"})
    public ResponseEntity<ProdutoDTO> salvaImage(@RequestParam Long id, @RequestPart MultipartFile image) throws IOException {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        String nomeImagemHash = produtoService.saveImage(id, image);

        produtoDTO.setImage(nomeImagemHash);
        ProdutoDTO savedProduto = produtoService.save(produtoDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }
    @GetMapping({"/todos-produtos"})
    public ResponseEntity<List<Produto>> getProdutos() {
        List<Produto> produto = this.produtoService.getProduto();
        return new ResponseEntity(produto, HttpStatus.OK);
    }

    @GetMapping({"/{name}"})
    public ResponseEntity<List<Produto>> getProdutosTOName(@PathVariable String name) {
        List<Produto> produto = this.produtoService.getProdutoTOName(name);
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
       try {
        produtoService.deletarProdutoPorId(id);
        return new ResponseEntity<>("Produto deletado com sucesso", HttpStatus.OK);
       }catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O produto com o ID " + id + " não existe!");
       }
    }

    @GetMapping("producto/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        try {
            Optional<Produto> produtoDTO = produtoService.getProdutoByID(id);
            return ResponseEntity.ok().body(produtoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("O produto com o ID " + id + " não foi encontrado!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProdito(@PathVariable Long id, @RequestBody Produto produto){
        produtoService.alteraProduto(produto,id);
        return new ResponseEntity<>("Produto Atualizado",HttpStatus.OK);
    }
}
