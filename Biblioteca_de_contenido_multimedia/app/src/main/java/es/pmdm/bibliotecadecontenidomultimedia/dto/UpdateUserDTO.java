package es.pmdm.bibliotecadecontenidomultimedia.dto;

import java.io.Serializable;
import java.util.List;

public class UpdateUserDTO implements Serializable {
    private int id;


    private List<ContenidoDto> contenidos;


    public UpdateUserDTO() {
    }

    public UpdateUserDTO(int id, List<ContenidoDto> contenidos) {
        this.id = id;
        this.contenidos = contenidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ContenidoDto> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<ContenidoDto> contenidos) {
        this.contenidos = contenidos;
    }
}
