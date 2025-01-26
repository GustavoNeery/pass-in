package nry.com.pass_in.controllers;

import jakarta.transaction.Transactional;
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

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteProduct(@PathVariable("id") String id) {
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        Product product1 = productRepository.findById(id);
        if(product.getName() != null){
            product1.setName(product.getName());
        }
        if(product.getDescription() != null){
            product1.setDescription(product.getDescription());
        }
        if(product.getPrice() != null){
            product1.setPrice(product.getPrice());
        }
        productRepository.save(product1);

        return product1;
    }

    @GetMapping("/by")
    public List<Product> findByName(@RequestParam("name") String name) {
        return productRepository.findByName(name);
    }

}
