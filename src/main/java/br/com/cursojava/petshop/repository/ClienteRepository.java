package br.com.cursojava.petshop.repository;

import br.com.cursojava.petshop.model.Cliente;
import br.com.cursojava.petshop.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente,Long> {
    public boolean existsById(Long id);

    public List<Cliente> findByNome(String nome);

}
