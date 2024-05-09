package com.example.API_biblioteca_multimedia.mappers.User;

import com.example.API_biblioteca_multimedia.dto.contenido.ContenidoDTO;
import com.example.API_biblioteca_multimedia.dto.contenido.UpdateContenidoDTO;
import com.example.API_biblioteca_multimedia.dto.user.CreateUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.UpdateUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.UserDTO;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> toDTO(List<User> users){
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    public User fromDTO(CreateUserDTO userDTO)
    {
        return modelMapper.map(userDTO, User.class);
    }

    public User fromDTO(UpdateUserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public User fromDTO(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        List<Contenido> contenidos = userDTO.getContenidos().stream()
                .map(contenidoDTO -> modelMapper.map(contenidoDTO, Contenido.class))
                .collect(Collectors.toList());
        user.setContenidos(contenidos);
        return user;
    }
}
