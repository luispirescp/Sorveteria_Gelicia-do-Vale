package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
//teste2
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository ) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> getProduto(){
        return (List<Produto>) produtoRepository.findAll();
    }

    public List<Produto> getProdutoPorName(String name){
        return produtoRepository.findByName(name);

    }
//
    public List<Produto> getProduto(int quantity){
        return produtoRepository.findByQuantity(quantity);
    }

    public Produto criarProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto alteraProduto(Produto produto){
       if(produtoRepository.existsById(produto.getId())){
           return produtoRepository.save(produto);
       }else {
           throw new RuntimeException(String.format("O produto com o ID %d não existe!", produto.getId()));
       }
    }

    public Produto deletaProduto(Produto produto){
        if(produtoRepository.existsById(produto.getId())){

            produtoRepository.delete(produto);
            return produto;
        }else {
            throw new RuntimeException(String.format("O produto com o ID %d não existe!", produto.getId()));
        }
    }
}
