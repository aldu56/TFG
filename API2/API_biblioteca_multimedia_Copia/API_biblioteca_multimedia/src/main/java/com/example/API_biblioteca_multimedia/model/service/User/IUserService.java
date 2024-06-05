package com.example.API_biblioteca_multimedia.model.service.User;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.Role;
import com.example.API_biblioteca_multimedia.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();
    User findById(int id);
    User addUser(User user, List<Long> rolIds);
    User updateUser(int id,User user);
    void deleteUser(User user);

    public User getUserByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);


   // public void eliminarContenidoDeUsuario(int userId, int contenidoId);

}
