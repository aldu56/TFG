package com.example.API_biblioteca_multimedia.exceptions.User;

public class UserRegistrationException extends RuntimeException
{
    public UserRegistrationException() {
        super();
    }

    public UserRegistrationException(String message) {
        super(message);
    }
}
