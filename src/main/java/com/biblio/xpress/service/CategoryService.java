package com.biblio.xpress.service;

import com.biblio.xpress.entity.Category;
import com.biblio.xpress.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryService {
    @Autowired
    private  CategoryRepository categoryRepository;
    public Category addnewCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getByName(categoryName);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.getById(categoryId);
    }
}
