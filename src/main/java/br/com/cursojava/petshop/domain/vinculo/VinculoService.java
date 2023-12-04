package br.com.cursojava.petshop.domain.vinculo;

import br.com.cursojava.petshop.domain.fornecedor.Fornecedor;
import br.com.cursojava.petshop.domain.fornecedor.FornecedorRepository;
import br.com.cursojava.petshop.domain.produto.Produto;
import br.com.cursojava.petshop.domain.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VinculoService {

    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public VinculoService(FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    public void vincularFornecedorProduto(Long fornecedorId, Long produtoId) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        fornecedor.getProdutos().add(produto);
        produto.getFornecedores().add(fornecedor);

        fornecedorRepository.save(fornecedor);
        produtoRepository.save(produto);
    }
}

