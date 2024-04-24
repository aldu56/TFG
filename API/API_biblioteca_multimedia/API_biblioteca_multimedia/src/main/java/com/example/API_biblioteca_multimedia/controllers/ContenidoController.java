package com.example.API_biblioteca_multimedia.controllers;

import com.example.API_biblioteca_multimedia.model.service.Contenido.IContenidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api")
@Tag(name = "Contenido", description = "Gestión de contenidos")
public class ContenidoController {

    @Autowired
    private IContenidoService contenidoService;


    private final Logger logger = LoggerFactory.getLogger(ContenidoController.class);

}
