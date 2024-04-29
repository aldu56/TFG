package com.example.API_biblioteca_multimedia.controllers;

import com.example.API_biblioteca_multimedia.dto.user.CreateUserDTO;
import com.example.API_biblioteca_multimedia.mappers.User.UserMapper;
import com.example.API_biblioteca_multimedia.model.entity.User;
import com.example.API_biblioteca_multimedia.model.service.User.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/users")
    public String getUsers() {
        return userService.findAll().toString();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        user = userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }


}
