package edu.learningspring.dreamshops.service.categories;

import edu.learningspring.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category addCategory(Category category);
    Category updateCategory(Category category, long id);

    List<Category> getAllCategories();
    void deleteCategoryById(Long id);
}
