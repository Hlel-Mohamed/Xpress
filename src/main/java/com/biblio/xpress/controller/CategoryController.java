package com.biblio.xpress.controller;

import com.biblio.xpress.entity.Category;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.service.CategoryService;
import com.biblio.xpress.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller

public class CategoryController {
    private final UserServiceImpl userService;
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(UserServiceImpl userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }
    @GetMapping("/myCategories")
    public String getMyCategories() {
                return "myCategory";
    }
    @GetMapping("/AllCategories")
    public String AllMyCategories() {
        return "AllCategories";
    }
    //Get Categories i'm subscribed to
    @GetMapping("/myFavCategories")
    public ResponseEntity<List<Category>> getMyCategory(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<UserEntity> user = userService.findUserByUsername(authentication.getName());
            if (user.isPresent()) {
                List<Category> userCategory = user.get().getFavoriteCategories();
                return ResponseEntity.ok(userCategory);
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return null;
    }
    @GetMapping("/Categories")
    public ResponseEntity<List<Category>> getAllCategories(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<UserEntity> user = userService.findUserByUsername(authentication.getName());
            if (user.isPresent()) {
                List<Category> categories = categoryService.getAllCategories();
                return ResponseEntity.ok(categories);
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return null;
    }
    //Add a new Category to my favorite category list
    @PostMapping("/newCategory/{categoryId}")
    public ResponseEntity<String> addNewCategory(@PathVariable Long categoryId, Authentication authentication){
        Optional<UserEntity> user = userService.findUserByUsername(authentication.getName());
        if (user.isPresent()) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category!=null) {
                System.out.println(category.getName());
                List<Category> userCategory = user.get().getFavoriteCategories();
                Iterator<Category> it  = userCategory.iterator();
                int count = 0;
                while (it.hasNext()){
                    Category c = it.next();
                    if (c.getId().equals(categoryId)){
                        count++;
                    }
                }
                if (count<1) {
                    userCategory.add(category);
                    user.get().setFavoriteCategories(userCategory);
                    userService.saveUser(user.get());
                    System.out.println(category.getName());
                    return ResponseEntity.ok(category.toString());
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return null;
    }


}
