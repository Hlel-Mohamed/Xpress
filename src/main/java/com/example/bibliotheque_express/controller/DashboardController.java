package com.example.bibliotheque_express.controller;

import com.example.bibliotheque_express.service.BookService;
import com.example.bibliotheque_express.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/")
    public String getDashboard(Model model){
        long totalBooks = bookService.getTotalBooks();
        long totalCategory = categoryService.getTotalCategory();

        model.addAttribute("totalBooks",totalBooks);
        model.addAttribute("totalCategory",totalCategory);
        return "index";
    }
}
