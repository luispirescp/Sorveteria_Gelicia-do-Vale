package br.com.cursojava.petshop.repository;

import br.com.cursojava.petshop.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    public boolean existsById(Long id);

    public List<Usuario> findByNome(String nome);


}
