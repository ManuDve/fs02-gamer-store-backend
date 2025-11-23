package cl.maotech.gamerstoreback.repository;

import cl.maotech.gamerstoreback.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, String> {
}