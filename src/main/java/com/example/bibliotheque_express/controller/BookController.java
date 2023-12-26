package com.example.bibliotheque_express.controller;

import com.example.bibliotheque_express.model.Book;
import com.example.bibliotheque_express.model.Category;
import com.example.bibliotheque_express.service.Impl.BookServiceImp;
import com.example.bibliotheque_express.service.Impl.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookServiceImp bookServiceImp;

    @Autowired
    CategoryServiceImp categoryServiceImp;

    @GetMapping("/")
    public String index() {
        return "index";

    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Book> bookList = bookServiceImp.getAll();
        if (bookList.size() > 0) {
            model.addAttribute("books", bookList);
        }
        return "book_list";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryServiceImp.getAll());
        return "book_add";
    }

    @PostMapping("/save_book")
    public String saveBook(Book book) {
        bookServiceImp.saveBook(book);
        return "redirect:/books/all";
    }

    //Update Crud
    @GetMapping("/update_book/{id}")
    public String updateBook(@PathVariable("id") Long id, Model model) {
        Book book = bookServiceImp.getById(id);
        model.addAttribute("book", book);

        return "book_update";
    }

    @GetMapping("/delete_book/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Book deleteBook = bookServiceImp.getById(id);
        bookServiceImp.delete(deleteBook);
        return "redirect:/books/all";
    }

    @GetMapping("/category/{categoryId}")
    public String getBooksByCategory(@PathVariable Long categoryId, Model model) {
        List<Book> bookCat = bookServiceImp.getBooksByCategoryId(categoryId);
        model.addAttribute("category", bookCat);
        return "";
    }
}
