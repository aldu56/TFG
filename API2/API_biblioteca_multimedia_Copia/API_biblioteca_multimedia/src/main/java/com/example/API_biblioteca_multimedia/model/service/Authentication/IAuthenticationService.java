package com.example.API_biblioteca_multimedia.model.service.Authentication;


import com.example.API_biblioteca_multimedia.model.entity.User;

public interface IAuthenticationService
{
    public User signup(User newUser);

    public User authenticate(User user);
}
