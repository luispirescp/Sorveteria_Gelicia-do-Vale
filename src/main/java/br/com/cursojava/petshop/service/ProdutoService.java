package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.dto.ProdutoDTO;
import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ranges.RangeException;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final Path fileStorageLocation;
    public static final String caminhoFinal = "C:\\Users\\luisp\\Documents\\Programacao\\spring\\sorveteria_gdelicia\\src\\main\\resources\\static\\public";

    public ProdutoService(ProdutoRepository produtoRepository, @Value(caminhoFinal) Path fileStorageLocation) throws IOException {
        this.produtoRepository = produtoRepository;
        this.fileStorageLocation = fileStorageLocation;
    }

    public List<Produto> getProduto() {
        return (List) this.produtoRepository.findAll();
    }

    public List<Produto> getProdutoTOName(String name) {
        return this.produtoRepository.findByParteNome(name);
    }

    public int getQuantityProduto(String name) {
        Produto produto = (Produto) produtoRepository.findByName(name);
        return produto != null ? produto.getQuantity() : 0;
    }

    public Boolean verificaNome(String name) {
        Produto produto = produtoRepository.findByName(name);
        if (produtoRepository.findByName(name).getName().equals(name) || produto != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getImageProduto(String name) {
        Produto produto = (Produto) produtoRepository.findByName(name);
        return produto != null ? produto.getImage() : "";
    }

    public Produto criarProduto(Produto produto) {
        return (Produto) this.produtoRepository.save(produto);
    }

    public void alteraProduto(ProdutoDTO produtoDTO, Long id) {
        //busca no banco os atributos que estão relacionados com o id e atribui a variavel produtoOprional
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            // passo para a variavel produto todos os valores capturado do banco
            Produto produto = produtoOptional.get();
            //nesse momento faço a alteração setando o valore que vem da requisição para os atributos do objeto resgatado
            produto.setName(produtoDTO.getName());
            produto.setBarcode(produtoDTO.getBarcode());
            produto.setDescription(produtoDTO.getDescription());
            produto.setQuantity(produtoDTO.getQuantity());
            produto.setPrice(produtoDTO.getPrice());
            produto.setTipo(produtoDTO.getTipo());
            //salvo o produto com os valores substituidos ateriormente os que estavam no banco para os que
            //chegaram via requisição;
            try {
                this.produtoRepository.save(produto);
            } catch (RuntimeException e) {
                throw new RuntimeException("Erro ao salvar o produto: " + e.getMessage());
            }
        } else {
            throw new RuntimeException(String.format("O produto com o ID %d não existe!", produtoDTO.getId()));
        }
    }
    public void deletarProdutoPorId(Long id) {
        if (this.produtoRepository.existsById(id)) {
            this.produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException(String.format("O produto com o ID %d não existe!", id));
        }
    }

    public Optional<Produto> getProdutoByID(Long id) {
        if (this.produtoRepository.existsById(id)) {
            return this.produtoRepository.findById(id);
        } else {
            throw new RuntimeException(String.format("O produto com o ID %d não existe!", id));
        }
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        Produto savedProduto = produtoRepository.save(produto);
        return convertToDTO(savedProduto);
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setName(produto.getName());
        produtoDTO.setBarcode(produto.getBarcode());
        produtoDTO.setDescription(produto.getDescription());
        produtoDTO.setQuantity(produto.getQuantity());
        produtoDTO.setTipo(produto.getTipo());
        produtoDTO.setPrice(produto.getPrice());
        return produtoDTO;
    }

    public void reduzirQuantidadeDoProduto(Long id, int quantidadeComprada){
        Optional<Produto> produtoOptional = Optional.ofNullable(produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado")));
        Produto produto = produtoOptional.get();
        int quantidadeAtual = produto.getQuantity();
        if(quantidadeAtual >= quantidadeComprada){
            produto.setQuantity(quantidadeAtual - quantidadeComprada);
            produtoRepository.save(produto);
        }else {
            throw new RuntimeException("Quantidade insuficiente em estoque");
        }
    }

    // Método para converter ProdutoDTO em Produto
    private Produto convertToEntity(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setId(produtoDTO.getId());
        produto.setName(produtoDTO.getName());
        produto.setBarcode(produtoDTO.getBarcode());
        produto.setDescription(produtoDTO.getDescription());
        produto.setQuantity(produtoDTO.getQuantity());
        produto.setTipo(produtoDTO.getTipo());
        produto.setPrice(produtoDTO.getPrice());
        return produto;
    }

    public Produto saveImage(Long id, MultipartFile image) throws IOException {
        Produto produto = null;
        String fileName = generateHash(image.getOriginalFilename()) + "_" + image.getOriginalFilename();
        saveImageLocalStorge(fileName, image);
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            produto = produtoOptional.get();
            try {
                produto.setImage(fileName);

                Produto savedProduto = produtoRepository.save(produto);
                return savedProduto;

            } catch (RuntimeException e) {
                throw new RuntimeException(String.format("O produto com o ID %d não existe!", id));
            }
        }
        return produto;
    }

    private void saveImageLocalStorge(String fileName, MultipartFile image) throws IOException {
        Path targetLocation = fileStorageLocation.resolve(fileName);
        Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    public static String generateHash(String imageName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(imageName.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            // Convertendo o hash em formato hexadecimal
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
