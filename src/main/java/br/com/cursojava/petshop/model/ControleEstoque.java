package br.com.cursojava.petshop.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class ControleEstoque {
    private Map<String, Produto> estoque;

    // Construtor
    public ControleEstoque() {
        this.estoque = new HashMap<>();
    }

    // Adicionar item ao estoque
//    public void adicionarItem(MultipartFile nome, int quantidade) {
//        if (estoque.containsKey(nome)) {
//            Produto item = estoque.get(nome);
//            item.setQuantity(item.getQuantity() + quantidade);
//        } else {
//            Produto novoItem = new Produto(nome, quantidade);
//            estoque.put(nome, novoItem);
//        }
//    }

    // Remover item do estoque
//    public void removerItem(String nome, int quantidade) {
//        if (estoque.containsKey(nome)) {
//            Produto item = estoque.get(nome);
//            int novaQuantidade = item.getQuantidade() - quantidade;
//            if (novaQuantidade >= 0) {
//                item.setQuantidade(novaQuantidade);
//            } else {
//                System.out.println("Quantidade insuficiente em estoque para remover.");
//            }
//        } else {
//            System.out.println("Item não encontrado no estoque.");
//        }
//    }

    // Obter a quantidade de um item no estoque

}