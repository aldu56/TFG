package com.example.API_biblioteca_multimedia.dto.user;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private int id;
    private String username;
    private String password;

    private List<Contenido> contenidos;

}
