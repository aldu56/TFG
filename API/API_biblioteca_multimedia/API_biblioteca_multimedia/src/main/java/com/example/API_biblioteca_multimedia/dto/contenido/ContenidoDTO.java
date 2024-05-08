package com.example.API_biblioteca_multimedia.dto.contenido;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContenidoDTO implements Serializable{

    private int id;

    private String titulo;
    private String categoria;

    private int anyo;

    private String autor;

    private String genero;

    private String url;

    private String duracion;

    private String descripcion;

    private float puntuacion;

    private String comentario;

}
