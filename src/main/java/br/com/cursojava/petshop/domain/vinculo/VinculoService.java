package br.com.cursojava.petshop.domain.vinculo;

import br.com.cursojava.petshop.domain.fornecedor.Fornecedor;
import br.com.cursojava.petshop.domain.fornecedor.FornecedorRepository;
import br.com.cursojava.petshop.domain.produto.Produto;
import br.com.cursojava.petshop.domain.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VinculoService {

    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public VinculoService(FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    public void vinculoForncedorProduto(Map<String, List<Map<String, Object>>> dados) {

        List<Map<String, Object>> fornecedores = dados.get("fornecedor");
        List<Map<String, Object>> produtos = dados.get("produto");
//        for (Map<String, Object> fornecedor : fornecedores) {
//            // Imprimir cada chave e valor dentro do mapa do fornecedor
//            for (Map.Entry<String, Object> entry : fornecedor.entrySet()) {
//                System.out.println("Chave: " + entry.getKey() + ", Valor: " + entry.getValue());
//            }

        if (fornecedores != null) {
            for (Map<String, Object> fornecedorData : fornecedores) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setName((String) fornecedorData.get("name"));
                fornecedor.setContato((String) fornecedorData.get("contato"));
                fornecedor.setCnpjCpf((String) fornecedorData.get("cnpjCpf"));
                fornecedor.setDescricao((String) fornecedorData.get("descricao"));

                fornecedorRepository.save(fornecedor);
            }
        }
        if (produtos != null) {
            for (Map<String, Object> produtoData : produtos) {
                Long productId = (Long) produtoData.get("id");
                Optional<Produto> produtoOptional = produtoRepository.findById(productId);

                if (produtoOptional.isPresent()) {
                    Produto produto = produtoOptional.get();
                    produto.setName((String) produtoData.get("name"));
                    produto.setQuantity((Integer) produtoData.get("quantity"));
                    produto.setPrice((Double) produtoData.get("price"));
                    produtoRepository.save(produto);
                } else {
                    Produto novoProduto = new Produto();
//                    novoProduto.setId(productId); // Defina o ID fornecido
                    novoProduto.setName((String) produtoData.get("name"));
                    novoProduto.setQuantity((Integer) produtoData.get("quantity"));
                    novoProduto.setPrice((Double) produtoData.get("price"));
                    produtoRepository.save(novoProduto);
                }

            }
        }
    }
}

