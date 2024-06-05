package com.example.API_biblioteca_multimedia.mappers.User;

import com.example.API_biblioteca_multimedia.dto.contenido.ContenidoDTO;
import com.example.API_biblioteca_multimedia.dto.contenido.UpdateContenidoDTO;
import com.example.API_biblioteca_multimedia.dto.user.Authentication.LoginUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.Authentication.RegisterUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.CreateUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.UpdateUserDTO;
import com.example.API_biblioteca_multimedia.dto.user.UserDTO;
import com.example.API_biblioteca_multimedia.mappers.Role.RolesListConverter;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.User;
import com.example.API_biblioteca_multimedia.model.repository.IRoleRepository;
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
    @Autowired
    IRoleRepository roleRepository;

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

    public User fromDTO(LoginUserDTO userDTO)
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

    public User fromDTO(RegisterUserDTO registerUserDTO)
    {
        //Mapea los nombres de la lista Actores a una lista de String.
        modelMapper.typeMap(RegisterUserDTO.class, User.class).addMappings(mapper -> {
            mapper.using(new RolesListConverter(roleRepository)).map(RegisterUserDTO::getRolIds, User::setRoles);
        });

        return modelMapper.map(registerUserDTO, User.class);
    }
}
