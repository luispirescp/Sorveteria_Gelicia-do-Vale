package br.com.cursojava.petshop.domain.produto;

import br.com.cursojava.petshop.domain.dto.ProdutoDTO;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) throws IOException {
        this.produtoRepository = produtoRepository;

    }

    public Page<Produto> getProdutoPage(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public List<Produto> getProduto() {
        return produtoRepository.findAll();
    }


    public List<Produto> getProdutoTOName(String name) {
        return this.produtoRepository.findByParteNome(name);
    }
    public int getQuantityProduto(String name) {
        Produto produto = (Produto) produtoRepository.findByName(name);
        return produto != null ? produto.getQuantity() : 0;
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
        try {
            Produto produto = convertToEntity(produtoDTO);
            Produto savedProduto = produtoRepository.save(produto);
            return convertToDTO(savedProduto);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao salvar produto");
        }
    }

    private Produto convertToEntity(ProdutoDTO produtoDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(produtoDTO, Produto.class);
        } catch (MappingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao converter DTO para entidade");
        }
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(produto, ProdutoDTO.class);
        } catch (MappingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao converter entidade para DTO");
        }
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
