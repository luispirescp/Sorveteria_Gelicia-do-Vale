package br.com.cursojava.petshop.model;

import br.com.cursojava.petshop.enums.TipoAnimal;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TipoAnimal tipo;

    private String nome;

    private String raca;

    private Integer idade;
}
