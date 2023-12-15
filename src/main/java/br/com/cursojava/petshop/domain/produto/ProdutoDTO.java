package br.com.cursojava.petshop.domain.produto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

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
    private LocalDate dataVenda;
    private String image;
}
