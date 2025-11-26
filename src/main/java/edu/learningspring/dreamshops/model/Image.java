package edu.learningspring.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity // This anotation declares this class to be a table in the application's database
public class Image {
    @Id  // our primary key is the id column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;


    // Images have a many-to-one relationship with products. Many images can be associated with a single product.
    @ManyToOne
    @JoinColumn(name="product_id") // An image is joined to a product based on the value in the product_id column
    private Product product;

}
