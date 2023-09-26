package br.com.cursojava.petshop.model;

import br.com.cursojava.petshop.enums.Tipo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Animal {

    //gerando id no banco
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String raca;
    private Integer idade;
}
