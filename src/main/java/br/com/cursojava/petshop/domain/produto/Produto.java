package br.com.cursojava.petshop.domain.produto;

import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Map;


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
    private String tipo;
    private double price;
    private String image;

    public Produto() {
    }
    public Produto(String nome, int quantity) {
        this.name = nome;
        this.quantity = quantity;
    }
}
