package com.biblio.xpress.repository;

import com.biblio.xpress.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getByName(String categoryName);
}
