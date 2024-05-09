package com.example.API_biblioteca_multimedia.dto.user;

import com.example.API_biblioteca_multimedia.dto.contenido.ContenidoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO implements Serializable {

    private String username;
    private String password;
    private List<ContenidoDTO> contenidos;
}
