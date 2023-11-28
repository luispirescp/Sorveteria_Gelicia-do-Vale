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
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
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
        String fileName = image.getOriginalFilename();
        String nomeImagem = generateHash(fileName)+"_"+fileName;
        Path targetLocation = fileStorageLocation.resolve(nomeImagem);
        image.transferTo(targetLocation);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(fileName)
                .toUriString();
        return nomeImagem;
    }

    public static String generateHash(String imageName) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(imageName.getBytes("UTF-8"));
            byte[] hashBytes = digest.digest();

            // Convertendo o array de bytes para uma representação hexadecimal
            Formatter formatter = new Formatter();
            for (byte b : hashBytes) {
                formatter.format("%02x", b);
            }
            String hash = formatter.toString();
            formatter.close();
            return hash;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
