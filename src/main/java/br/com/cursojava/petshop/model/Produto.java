package br.com.cursojava.petshop.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String barcode;
    private String description;
    private int quantity;
    private Tipo tipo;
    private double price;
    @Lob
    @Column(
            length = 900
    )
    private byte[] image;
}
