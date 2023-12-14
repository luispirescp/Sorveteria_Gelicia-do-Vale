package br.com.cursojava.petshop.domain.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Produto,Long> {

    boolean existsById(Long id);

    Produto findByName(String name);

    List<Produto> findAll();
    Page<Produto> findAll(Pageable pageable);
    Optional<Produto> findById(Long id);
    @Query("SELECT p FROM Produto p WHERE p.name LIKE %:parteNome%")
    List<Produto> findByParteNome(String parteNome);
    default List<Produto> findByQuantity(int quantity) {
        return null;
    }
    List<Produto> findByPrice(double price);
}
