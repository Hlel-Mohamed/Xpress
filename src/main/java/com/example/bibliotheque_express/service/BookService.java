package com.example.bibliotheque_express.service;

import com.example.bibliotheque_express.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {
    void saveBook(Book book);
    List<Book> getAll();
    Book getById(Long id);
    void delete(Book book);
    List<Book> getBooksByCategoryId(Long categoryId);

    long getTotalBooks();

}
