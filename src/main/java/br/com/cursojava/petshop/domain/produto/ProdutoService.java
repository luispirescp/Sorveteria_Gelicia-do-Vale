package br.com.cursojava.petshop.domain.produto;

import br.com.cursojava.petshop.domain.dto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final Path fileStorageLocation;
    public static final String caminhoFinal = "C:\\Users\\luisp\\Documents\\Programacao\\spring\\Sorveteria_Gelicia-do-Vale\\src\\main\\resources\\static\\public";

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

    public String getImageProduto(String name) {
        Produto produto = (Produto) produtoRepository.findByName(name);
        return produto != null ? produto.getImage() : "";
    }
    public Produto alteraProduto(ProdutoDTO produtoDTO, Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            Produto produtoConvertido = convertToEntity(produtoDTO);
             produtoConvertido.setId(produto.getId());
            try {
                return this.produtoRepository.save(produtoConvertido);
            } catch (DataAccessException e) {
                throw new RuntimeException("Erro ao salvar o produto: " + e.getRootCause().getMessage());
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
        produto.setImage(produtoDTO.getImage());
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

    public List<Produto> saveListProdutos(List<Produto> listaProdutos) {
        List<Produto> produtosSalvos = new ArrayList<>();
        for (Produto produto : listaProdutos) {
            // Realize suas validações aqui, se necessário
            if (produtoIsValid(produto)) {
                Produto produtoSalvo = produtoRepository.save(produto);
                produtosSalvos.add(produtoSalvo);
            } else {
                // Lógica para lidar com produtos inválidos, se necessário
                System.out.println("Produto inválido: " + produto.getName());
            }
        }
        return produtosSalvos;
    }

    private boolean produtoIsValid(Produto produto) {
        // Implemente sua lógica de validação aqui
        // Por exemplo, verifique se os campos obrigatórios estão preenchidos corretamente
        return produto.getName() != null && !produto.getName().isEmpty()
                && produto.getPrice() > 0 && produto.getQuantity() > 0;
    }

    @Transactional
    public void reducesStock(List<ProdutoDTO> produtoDTOS) {
        produtoDTOS.forEach(produtoDTO -> {
            produtoRepository.findById(produtoDTO.getId()).ifPresent(produto -> {
                reduceStockIfPossible(produto, produtoDTO.getQuantity());
            });
        });
    }
    private void reduceStockIfPossible(Produto produto, int quantityToReduce) {
        if (produto.getQuantity() >= quantityToReduce) {
            int novaQuantidade = produto.getQuantity() - quantityToReduce;
            produto.setQuantity(novaQuantidade);
            produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Não é possível reduzir o estoque para o produto " + produto.getId());
        }
    }
}
