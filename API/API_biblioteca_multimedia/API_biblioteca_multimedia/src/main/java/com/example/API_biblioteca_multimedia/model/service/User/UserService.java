package com.example.API_biblioteca_multimedia.model.service.User;

import com.example.API_biblioteca_multimedia.exceptions.User.UserNotFoundException;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.User;
import com.example.API_biblioteca_multimedia.model.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(null); //TODO ---------> () -> UserNotFoundException("Error al encontrar al User con ID: " + id)
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Error al buscar el user " + id));

        newUser.setId(user.getId());

        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userRepository.getUserByUsernameAndPassword(username, password);
    }

//    @Override
//    public void eliminarContenidoDeUsuario(int userId, int contenidoId) {
//
//    }
}
