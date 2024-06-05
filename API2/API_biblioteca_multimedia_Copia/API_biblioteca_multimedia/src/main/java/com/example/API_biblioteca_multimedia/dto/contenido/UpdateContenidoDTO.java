package com.example.API_biblioteca_multimedia.dto.contenido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContenidoDTO implements Serializable {
    private Long id;
    private String comentario;
    private float puntuacion;
}
