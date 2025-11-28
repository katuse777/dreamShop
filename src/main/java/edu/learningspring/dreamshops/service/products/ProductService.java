package edu.learningspring.dreamshops.service.products;

import edu.learningspring.dreamshops.exceptions.ProductNotFoundException;
import edu.learningspring.dreamshops.model.Category;
import edu.learningspring.dreamshops.model.Product;
import edu.learningspring.dreamshops.repository.CategoryRepository;
import edu.learningspring.dreamshops.repository.ProductRepository;
import edu.learningspring.dreamshops.requests.AddProductRequest;
import edu.learningspring.dreamshops.requests.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Product addProduct(AddProductRequest request) {
        // check if category is found in the database;
        // if it is, set it as the new Product category,
        // otherwise save it as a new category , then set it as the new Product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet( () -> {
                    Category newCategory = new Category( request.getCategory().getName() );
                    return categoryRepository.save(newCategory);
                } );
        request.setCategory( category );
        return productRepository.save( createProduct(request, category) );
    }

    private Product createProduct(AddProductRequest request, Category category) {
        // helper method for the addProduct method.
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                request.getCategory()
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow( () -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse( productRepository::delete,
                () -> {throw new ProductNotFoundException("Product Now Found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById( productId )
                .map( existingProduct -> updateExistingProduct( existingProduct, request ) )
                .map( productRepository::save )
                .orElseThrow( () -> new ProductNotFoundException("Product Not Found!") );
                
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        // helper method for the updateProduct method
        existingProduct.setName( request.getName() );
        existingProduct.setBrand( request.getBrand() );
        existingProduct.setPrice( request.getPrice() );
        existingProduct.setInventory( request.getInventory() );
        existingProduct.setDescription( request.getDescription() );
        existingProduct.setCategory( request.getCategory() );

        Category category = categoryRepository.findByName( request.getCategory().getName() );
        existingProduct.setCategory( category );
        return  existingProduct;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
