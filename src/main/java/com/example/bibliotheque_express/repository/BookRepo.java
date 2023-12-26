package com.example.bibliotheque_express.repository;

import com.example.bibliotheque_express.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository <Book,Long>{
    List<Book> findByCategory_Id(Long categoryId);

}
