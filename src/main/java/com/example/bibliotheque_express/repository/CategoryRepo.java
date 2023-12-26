package com.example.bibliotheque_express.repository;

import com.example.bibliotheque_express.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
