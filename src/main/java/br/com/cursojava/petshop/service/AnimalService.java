package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.model.Animal;
import br.com.cursojava.petshop.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
//teste
    private final AnimalRepository animalRepository ;

    public AnimalService(AnimalRepository animalRepository ) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> getAnimal(){
        return (List<Animal>) animalRepository.findAll();
    }

    public List<Animal> getAnimalPorNome(String nome){
        return animalRepository.findByNome(nome);
    }

    public List<Animal> getAnimalPorRaca(String raca){
        return animalRepository.findByRaca(raca);
    }

    public List<Animal> getAnimalPorIdade(Integer idade){
        return animalRepository.findByIdade(idade);
    }

    public Animal criarAnimal(Animal animal){
        return animalRepository.save(animal);
    }

    public Animal alteraAnimal(Animal animal){
       if(animalRepository.existsById(animal.getId())){
           return animalRepository.save(animal);
       }else {
           throw new RuntimeException(String.format("O animal com o ID %d não existe!", animal.getId()));
       }
    }

    public Animal deletaAnimal(Animal animal){
        if(animalRepository.existsById(animal.getId())){
             animalRepository.delete(animal);
            return animal;
        }else {
            throw new RuntimeException(String.format("O animal com o ID %d não existe!", animal.getId()));
        }
    }
}
