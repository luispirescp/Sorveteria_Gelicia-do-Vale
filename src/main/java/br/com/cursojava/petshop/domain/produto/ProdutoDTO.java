package br.com.cursojava.petshop.domain.produto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String name;
    private String barcode;
    private String description;
    private int quantity;
    private String tipo;
    private double price;
    private String image;
}
