package com.biblio.xpress.exception;

public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException(String message) {
        super(message);
    }
    public UsernameTakenException(){
        super("Username already taken");
    }
}
