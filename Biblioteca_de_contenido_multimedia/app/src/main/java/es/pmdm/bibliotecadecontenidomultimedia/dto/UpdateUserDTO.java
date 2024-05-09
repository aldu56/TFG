package es.pmdm.bibliotecadecontenidomultimedia.dto;

import java.io.Serializable;
import java.util.List;

public class UpdateUserDTO implements Serializable {
    private int id;
    private String username;
    private String password;


    private List<ContenidoDto> contenidos;
}
