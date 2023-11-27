package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.exception.TipoNaoEncontradoException;
import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.model.Tipo;
import br.com.cursojava.petshop.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.cursojava.petshop.model.Tipo.fromString;

@Service
public class ProdutoService {
 private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
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
}
