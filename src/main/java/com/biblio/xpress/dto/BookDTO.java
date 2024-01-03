package com.biblio.xpress.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {
    private Long id;
    @NotNull
    private String isbn;
    @NotNull
    private String title;
    private String author;
    private Date publicationDate;
    private Long categoryId;
    private int numberOfCopies;

}
