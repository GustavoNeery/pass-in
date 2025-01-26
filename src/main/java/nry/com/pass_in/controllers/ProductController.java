package nry.com.pass_in.controllers;

import nry.com.pass_in.repositories.ProductRepository;
import org.springframework.web.bind.annotation.*;
import nry.com.pass_in.domain.Product;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        System.out.println("Este é o novo produto: " + product.getName());

        var id = UUID.randomUUID().toString();
        product.setId(id);
        productRepository.save(product);
        return product;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable String id) {
        return productRepository.findById(id);
    }

}
