package nry.com.pass_in.repositories;

import nry.com.pass_in.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(String id);
}
