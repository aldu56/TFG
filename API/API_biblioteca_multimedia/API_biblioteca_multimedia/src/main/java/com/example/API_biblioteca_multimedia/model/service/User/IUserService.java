package com.example.API_biblioteca_multimedia.model.service.User;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();
    User findById(int id);
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(User user);

    public User getUserByUsernameAndPassword(String username, String password);
}
