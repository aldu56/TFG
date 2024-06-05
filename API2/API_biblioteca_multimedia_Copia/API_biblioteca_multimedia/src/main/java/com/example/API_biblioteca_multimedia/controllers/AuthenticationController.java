package com.example.API_biblioteca_multimedia.controllers;

import com.example.API_biblioteca_multimedia.dto.user.Authentication.LoginUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.Authentication.RegisterUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.TokenDTO;
import com.example.API_biblioteca_multimedia.exceptions.Response;
import com.example.API_biblioteca_multimedia.mappers.User.UserMapper;
import com.example.API_biblioteca_multimedia.model.entity.User;
import com.example.API_biblioteca_multimedia.model.service.Authentication.IAuthenticationService;
import com.example.API_biblioteca_multimedia.model.service.User.IUserService;
import com.example.API_biblioteca_multimedia.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Response> register(@RequestBody RegisterUserDTO registerUserDto)
    {

        registerUserDto.setRolIds(Collections.singletonList(1L));

        User user = userMapper.fromDTO(registerUserDto);

        authenticationService.signup(user);

        String message = "Usuario registrado correctamente";

        return new ResponseEntity<Response>(Response.noErrorResponse(message), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDTO loginUserDto)
    {
        try {
            User loginUser = userMapper.fromDTO(loginUserDto);
            User authenticatedUser = authenticationService.authenticate(loginUser);
            String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);
            TokenDTO dto = new TokenDTO(jwtToken);
            return ResponseEntity.ok(dto.getToken());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }
}
