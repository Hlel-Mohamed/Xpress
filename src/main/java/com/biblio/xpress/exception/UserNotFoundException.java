package com.biblio.xpress.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("UserEntity not found with id " + id + ".");
    }
}
