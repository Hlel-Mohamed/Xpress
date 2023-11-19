package com.biblio.xpress.entity;

import com.biblio.xpress.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String isbn;
    @NotNull
    private String title;
    private String author;
    private Date publicationDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;
    private static int numberOfCopies;
}
