package com.example.bibliotheque_express.controller;

import com.example.bibliotheque_express.model.Category;
import com.example.bibliotheque_express.service.Impl.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImp categoryServiceImp;

    @GetMapping("/all")
    public String getAll(Model model){
        List<Category> Categorylist = categoryServiceImp.getAll();
        if(Categorylist.size() >0){
            model.addAttribute("category",Categorylist);
        }

        return "category_list";
    }

    @GetMapping("/add")
    public String addCategory(Model model){
        model.addAttribute("category",new Category());
        return "category_add";
    }

    @PostMapping("/save_category")
    public String saveCategory(Category category){
        categoryServiceImp.saveCategory(category);
        return "redirect:/category/all";
    }

    @GetMapping("/update_category/{id}")
    public String updateCategory(@PathVariable("id") Long id,Model model){
        Category updateCategory = categoryServiceImp.getById(id);
        model.addAttribute("category",updateCategory);
        return "category_update";
    }

    @GetMapping("/delete_category/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        Category deleteCategory = categoryServiceImp.getById(id);
        categoryServiceImp.delete(deleteCategory);
    }

}
