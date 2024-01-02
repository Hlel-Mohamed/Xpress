package com.biblio.xpress.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private Category category;
    private  int numberOfCopies;
}
