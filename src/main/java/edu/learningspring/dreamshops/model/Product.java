package edu.learningspring.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;


    // The product class has a many-to-one relationship with the Category class. That is, many products can belong to
    // a single class.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "category_id" )
    private Category category;


    // The product class has a one-to-many relationship with the image class too. However, this time one product can
    // be associated with many images.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // any unreferenced images in the database will be deleted.
    private List<Image> images;
}
