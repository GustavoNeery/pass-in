package nry.com.pass_in.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String description;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product(){

    }

    public Product(String name, String id, Double price, String description) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.description = description;
    }
}
