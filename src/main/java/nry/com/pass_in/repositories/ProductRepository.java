package nry.com.pass_in.repositories;

import nry.com.pass_in.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(String id);
    void deleteById(String id);
    List<Product> findByName(String name);
}
