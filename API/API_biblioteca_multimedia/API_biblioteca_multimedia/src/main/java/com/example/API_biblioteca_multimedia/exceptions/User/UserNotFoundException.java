package com.example.API_biblioteca_multimedia.exceptions.User;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(int id) {
        super ("User no existente: " + id);
    }
}
