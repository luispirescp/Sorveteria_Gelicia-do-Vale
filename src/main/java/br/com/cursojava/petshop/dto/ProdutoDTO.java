package br.com.cursojava.petshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProdutoDTO {
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("description")
    private String description;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("price")
    private double price;
    @JsonProperty("image")
    private String image;
}
