package com.example.API_biblioteca_multimedia.model.repository;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

    public User getUserByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    // public void eliminarContenidoDeUsuario(int userId, int contenidoId);

}
