package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Animal;
import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.service.AnimalService;
import br.com.cursojava.petshop.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnimalController {
//teste2 25-09
    private final AnimalService animalService;

    public AnimalController(AnimalService  animalService) {
        this.animalService = animalService;
    }

    //Cria Animal
    @PostMapping(value = "/salva-animal", consumes = "application/json")
    public ResponseEntity<Animal> criaAnimal(@RequestBody Animal animal) {
        animal = animalService.criarAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }

    //Busca todos os animais
    @GetMapping("/todos-animais")
    public ResponseEntity<List<Animal>> getTodosUsuarios() {
        List<Animal> animais = animalService.getAnimal();
        return new ResponseEntity<>(animais, HttpStatus.OK);
    }

    //Buscar por nome
    @GetMapping("/animais/{nome}")
    public ResponseEntity<List<Animal>> getAnimalPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(animalService.getAnimalPorNome(nome), HttpStatus.OK);
    }

    //Busca animal por raca
    @GetMapping("/animal/{raca}")
    public ResponseEntity<List<Animal>> getAnimalPorRaca(@PathVariable String raca) {
        return new ResponseEntity<>(animalService.getAnimalPorRaca(raca), HttpStatus.OK);
    }

    //Busca animal por idade
    @GetMapping("/animal-idade/{idade}")
    public ResponseEntity<List<Animal>> getAnimalPorRaca(@PathVariable Integer idade) {
        return new ResponseEntity<>(animalService.getAnimalPorIdade(idade), HttpStatus.OK);
    }

    //Altera animal
    @PutMapping (value = "/altera-animal", consumes = "application/json")
    public ResponseEntity<Animal> alteraAnimal(@RequestBody Animal animal) {
        animal = animalService.alteraAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }
    //Delete animal
    @DeleteMapping(value = "/deleta-animal", consumes = "application/json")
    public ResponseEntity<Animal> deletaAnimal(@RequestBody Animal animal){
        animal = animalService.deletaAnimal(animal);
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }
}
