package com.example.bibliotheque_express.service;

import com.example.bibliotheque_express.model.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);
    List<Category> getAll();
    Category getById(Long id);
    void delete(Category category);
}
