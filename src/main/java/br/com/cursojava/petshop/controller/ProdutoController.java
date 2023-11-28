package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.model.Tipo;
import br.com.cursojava.petshop.service.ProdutoService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.lang.System.in;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@RestController
@CrossOrigin(origins = "*")
public class ProdutoController {
    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    @PostMapping(value = {"/create-produto"},consumes = {"multipart/form-data"})
    public ResponseEntity<Produto> criaProduto(@RequestParam String name, @RequestParam Tipo tipo, @RequestParam String description, @RequestParam String barcode, @RequestParam double price, @RequestParam int quantity, @RequestPart MultipartFile image) throws IOException {

        String nomeImagemHash = produtoService.saveImage(image);
        Produto produto = new Produto();
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

    @GetMapping({"/file"})
    public  byte[] getImage() throws IOException {
            Resource resource = new ClassPathResource("\\..\\..\\\\pequena.png");
            Path path = Paths.get(resource.getURI());
//            // Lê a imagem como array de bytes
            return Files.readAllBytes(path);
}


    @GetMapping({"/produto/{name}"})
    public ResponseEntity<List<Produto>> getAnimalPorNome(@PathVariable String name) {
        return new ResponseEntity(this.produtoService.getProdutoPorName(name), HttpStatus.OK);
    }
}
