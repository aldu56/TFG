package com.example.API_biblioteca_multimedia.dto.contenidoGuardado;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateContenidoGuardadoDTO implements Serializable {

    @Schema(description = "Id del contenido a guardar en el usuario")
    @NotBlank
    private int id_Contenido;

    @Schema(description = "Id del usuario")
    @NotBlank
    private int id_user;


}
