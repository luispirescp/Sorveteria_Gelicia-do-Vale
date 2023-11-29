package br.com.cursojava.petshop.repository;

import br.com.cursojava.petshop.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto,Long> {

    boolean existsById(Long id);

    Produto findByName(String name);

    @Query("SELECT p FROM Produto p WHERE p.name LIKE %:parteNome%")
    List<Produto> findByParteNome(String parteNome);
    default List<Produto> findByQuantity(int quantity) {
        return null;
    }
    List<Produto> findByPrice(double price);
}
