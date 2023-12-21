package com.biblio.xpress.exception;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException(String message) {
        super(message);
    }

    public EmailTakenException(){
        super("Email already taken!");
    }
}
