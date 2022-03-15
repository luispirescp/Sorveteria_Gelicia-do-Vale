package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Animal;
import br.com.cursojava.petshop.model.Cliente;
import br.com.cursojava.petshop.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping(value = "/criar-animal", consumes = "application/json")
    public ResponseEntity<Animal> criarAnimal(@RequestBody Animal animal) {
        animal = animalService.criarAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }

    //Read do CRUD
    @GetMapping("/todos-animais")
    public ResponseEntity<List<Animal>> getTodosAnimais() {
        List<Animal> usuarios = animalService.getAnimais();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    //Alter do Crud
    @PutMapping(value = "/altera-animal", consumes = "application/json")
    public ResponseEntity<Animal> alteraAnimal(@RequestBody Animal animal) {
        animal = animalService.alteraAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.ACCEPTED);
    }

    //Delete do CRUD
    @DeleteMapping(value = "/deleta-animal", consumes = "application/json")
    public ResponseEntity<Animal> deletaAnimal(@RequestBody Animal animal){
        animal = animalService.deletaAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.ACCEPTED);
    }
}
