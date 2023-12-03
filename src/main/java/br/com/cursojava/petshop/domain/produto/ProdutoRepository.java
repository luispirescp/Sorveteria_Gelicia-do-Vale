package br.com.cursojava.petshop.domain.produto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto,Long> {

    boolean existsById(Long id);

    Produto findByName(String name);
    Optional<Produto> findById(Long id);
    @Query("SELECT p FROM Produto p WHERE p.name LIKE %:parteNome%")
    List<Produto> findByParteNome(String parteNome);
    default List<Produto> findByQuantity(int quantity) {
        return null;
    }
    List<Produto> findByPrice(double price);
}
