package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.exception.TipoNaoEncontradoException;
import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.model.Tipo;
import br.com.cursojava.petshop.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static br.com.cursojava.petshop.model.Tipo.fromString;

@Service
public class ProdutoService {
 private final ProdutoRepository produtoRepository;
    private final Path fileStorageLocation;

    public static final String caminhoFinal = "C:\\Users\\luisp\\Documents\\Programacao\\spring\\sorveteria_gdelicia\\src\\main\\resources\\static\\public";

    public ProdutoService(ProdutoRepository produtoRepository,@Value(caminhoFinal) Path fileStorageLocation) throws IOException {
        this.produtoRepository = produtoRepository;
        this.fileStorageLocation = fileStorageLocation;
    }

    public String caminhoImage(){
    return caminhoFinal+"\\Teste_tiger.png";
    }
    public List<Produto> getProduto() {
        return (List)this.produtoRepository.findAll();
    }
    public List<Produto> getProdutoPorName(String name) {
        return this.produtoRepository.findByName(name);
    }

    public List<Produto> getProduto(int quantity) {
        return this.produtoRepository.findByQuantity(quantity);
    }

    public Produto criarProduto(Produto produto) {

        return (Produto) this.produtoRepository.save(produto);
    }

    public Produto alteraProduto(Produto produto) {
        if (this.produtoRepository.existsById(produto.getId())) {
            return (Produto)this.produtoRepository.save(produto);
        } else {
            throw new RuntimeException(String.format("O produto com o ID %d não existe!", produto.getId()));
        }
    }

    public Produto deletaProduto(Produto produto) {
        if (this.produtoRepository.existsById(produto.getId())) {
            this.produtoRepository.delete(produto);
            return produto;
        } else {
            throw new RuntimeException(String.format("O produto com o ID %d não existe!", produto.getId()));
        }
    }

    public String saveImage(MultipartFile image) throws IOException {
        String fileName = "Teste_"+image.getOriginalFilename();
        Path targetLocation = fileStorageLocation.resolve(fileName);
        image.transferTo(targetLocation);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();
        int nomeImagem = fileName.hashCode()*84621;
        String nomeImagem_final = String.valueOf(nomeImagem)+"_"+fileName;
        return nomeImagem_final;
    }
}
