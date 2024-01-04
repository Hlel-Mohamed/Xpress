package com.example.bibliotheque_express.service.Impl;

import com.example.bibliotheque_express.model.Category;
import com.example.bibliotheque_express.repository.CategoryRepo;
import com.example.bibliotheque_express.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public void saveCategory(Category category) {
        if(category.getId() == null){
            categoryRepo.save(category);
        }else{
            Category updateCategory = categoryRepo.findById(category.getId()).get();
            updateCategory.setName(category.getName());
            updateCategory.setDescription(category.getDescription());
            categoryRepo.save(updateCategory);
        }
    }

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepo.findById(id).get();
    }

    @Override
    public void delete(Category category) {
        categoryRepo.delete(category);
    }

    public long getTotalCategory(){
        return categoryRepo.count();
    }
}
