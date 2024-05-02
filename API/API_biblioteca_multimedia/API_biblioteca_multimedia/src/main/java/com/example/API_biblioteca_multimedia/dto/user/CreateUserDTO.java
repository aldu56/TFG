package com.example.API_biblioteca_multimedia.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO implements Serializable {

    @Schema(description = "username")
    @NotBlank
    private String username;

    @Schema(description = "password")
    @NotBlank
    private String password;


}
