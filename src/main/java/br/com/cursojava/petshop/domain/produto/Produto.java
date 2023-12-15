package br.com.cursojava.petshop.domain.produto;

import br.com.cursojava.petshop.domain.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String barcode;
    private String description;
    private int quantity;
    private int quantity_vendida;
    private String tipo;
    private double price;
    private String image;
    private LocalDate data_venda;
    @Enumerated(EnumType.STRING)
    private Status status_compra;


    public Produto() {
    }
    public Produto(String nome, int quantity) {
        this.name = nome;
        this.quantity = quantity;
    }

    public Status foiComprado() {
        return this.status_compra = Status.COMPRADO;
    }

//    @ManyToMany(mappedBy = "produtos")
//    private List<Fornecedor> fornecedores = new ArrayList<>();
}
