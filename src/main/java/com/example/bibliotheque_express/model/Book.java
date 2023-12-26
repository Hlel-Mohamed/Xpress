package com.example.bibliotheque_express.model;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

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


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private int numberOfCopies;
}
