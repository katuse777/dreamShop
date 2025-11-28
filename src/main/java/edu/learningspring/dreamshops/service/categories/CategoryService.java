package edu.learningspring.dreamshops.service.categories;

import edu.learningspring.dreamshops.exceptions.AlreadyExistsException;
import edu.learningspring.dreamshops.exceptions.ResourceNotFoundException;
import edu.learningspring.dreamshops.model.Category;
import edu.learningspring.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Category Not Found!") );
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter( c -> !categoryRepository.existsByName( c.getName() ) )
                .map( categoryRepository::save )
                .orElseThrow( () -> new AlreadyExistsException(category.getName() + " already exists.") );
    }

    @Override
    public Category updateCategory(Category category, long id) {
        return Optional.ofNullable( getCategoryById(id) )
                .map( oldCategory -> {
                    oldCategory.setName(category.getName() );
                    return categoryRepository.save(oldCategory);
                } ).orElseThrow( () -> new ResourceNotFoundException("Category Not Found!") );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category Not Found!");
                });
    }
}
