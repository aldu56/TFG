package com.example.API_biblioteca_multimedia.model.service.Authentication;


import com.example.API_biblioteca_multimedia.model.entity.User;
import com.example.API_biblioteca_multimedia.model.repository.IUserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User signup(User newUser) {
        try {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }catch (Exception ex){
            System.err.println("Error creando el usuario: " + ex.getMessage());
        }
        return userRepository.save(newUser);
    }

    @Override
    public User authenticate(User user) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            return userRepository.findByUsername(user.getUsername())
                    .orElseThrow();
    }
}
