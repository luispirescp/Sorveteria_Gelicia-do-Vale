package br.com.cursojava.petshop.model;

import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;


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
    private String image;
}
