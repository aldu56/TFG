package com.example.API_biblioteca_multimedia.controllers;

import com.example.API_biblioteca_multimedia.exceptions.Response;
import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api")
@Tag(name = "Contenido Guardado", description = "Gestión de contenido guardado por usuarios")
public class ContenidoGuardadoController {

}
