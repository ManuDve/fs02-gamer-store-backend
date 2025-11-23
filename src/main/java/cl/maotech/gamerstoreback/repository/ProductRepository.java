package cl.maotech.gamerstoreback.repository;

import cl.maotech.gamerstoreback.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @EntityGraph(attributePaths = {"characteristics", "stock"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"characteristics", "stock"})
    Optional<Product> findById(String id);

    @EntityGraph(attributePaths = {"characteristics", "stock"})
    List<Product> findByCategory(String category);

    @EntityGraph(attributePaths = {"characteristics", "stock"})
    List<Product> findByNameContainingIgnoreCase(String name);
}
