package com.example.API_biblioteca_multimedia.controllers;

import com.example.API_biblioteca_multimedia.dto.contenido.ContenidoDTO;
import com.example.API_biblioteca_multimedia.dto.user.CreateUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.UpdateUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.UserDTO;
import com.example.API_biblioteca_multimedia.mappers.User.UserMapper;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.User;
import com.example.API_biblioteca_multimedia.model.service.User.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        user = userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @GetMapping("/userById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        User user = userService.findById(id);
        UserDTO userDTO = userMapper.toDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @Operation(summary = "Método que actualiza un User")
    @PutMapping("/updateUsers/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDTO newUserDTO) {
        User user = null;
        Map<String, Object> errores  = new HashMap<String, Object>();

        try {
            User newUser = userMapper.fromDTO(newUserDTO);
            user = userService.updateUser(id, newUser);
        } catch (Exception ex) {
            errores.put("mensaje", "Error al modificar el user");
            errores.put("mensaje", ex.getMessage());
            return new ResponseEntity<>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
