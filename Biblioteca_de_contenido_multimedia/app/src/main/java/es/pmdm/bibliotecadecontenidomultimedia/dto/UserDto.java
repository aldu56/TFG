package es.pmdm.bibliotecadecontenidomultimedia.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;

public class UserDto implements Serializable {
    private String username;
    private String password;

    private List<Long> rolIds;

    private List<ContenidoDto> contenidos;


    public UserDto() {
    }

    public UserDto(String username, String password, List<Long> rolIds, List<ContenidoDto> contenidos) {
        this.username = username;
        this.password = password;
        this.rolIds = rolIds;
        this.contenidos = contenidos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ContenidoDto> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<ContenidoDto> contenidos) {
        this.contenidos = contenidos;
    }

    public List<Long> getRolIds() {
        return rolIds;
    }

    public void setRolIds(List<Long> rolIds) {
        this.rolIds = rolIds;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", contenidos=" + contenidos.toString() +
                '}';
    }
}

