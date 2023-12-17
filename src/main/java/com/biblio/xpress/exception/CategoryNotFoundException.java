package com.biblio.xpress.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id) {
        super("Category not found with id " + id + ".");
    }
    public CategoryNotFoundException(String name) {
        super("Category not found with name " + name + ".");
    }
}
