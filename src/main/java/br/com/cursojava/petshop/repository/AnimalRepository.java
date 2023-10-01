package br.com.cursojava.petshop.repository;

import br.com.cursojava.petshop.enums.Tipo;
import br.com.cursojava.petshop.model.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends CrudRepository<Animal,Long> {

    public boolean existsById(Long id);

    public List<Animal> findByNome(String nome);

    public List<Animal> findByRaca(String raca);

    public List<Animal> findByIdade(Integer idade);

}
