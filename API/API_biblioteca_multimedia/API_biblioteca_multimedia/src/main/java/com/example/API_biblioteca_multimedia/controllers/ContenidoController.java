package com.example.API_biblioteca_multimedia.controllers;

import com.example.API_biblioteca_multimedia.dto.contenido.ContenidoDTO;
import com.example.API_biblioteca_multimedia.exceptions.Response;
import com.example.API_biblioteca_multimedia.mappers.Contenido.ContenidoMapper;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.service.Contenido.IContenidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api")
@Tag(name = "Contenido", description = "Gestión de contenidos")
public class ContenidoController {

    @Autowired
    private IContenidoService contenidoService;

    @Autowired
    ContenidoMapper contenidoMapper;




    private final Logger logger = LoggerFactory.getLogger(ContenidoController.class);

    @GetMapping("/contenidos")
    public ResponseEntity<List<ContenidoDTO>> getContenidos(){
        List<Contenido> contenidos = contenidoService.findAll();
        List<ContenidoDTO> contenidosDTO = contenidoMapper.toDTO(contenidos);
        return new ResponseEntity<>(contenidosDTO, HttpStatus.OK);
    }

    @GetMapping("/contenidosNormales")
    public ResponseEntity<List<Contenido>> getContenidosNormales(){
        List<Contenido> contenidos = contenidoService.findAll();
        return new ResponseEntity<>(contenidos, HttpStatus.OK);
    }



    @GetMapping("/contenidosByAnyo/{anyo}")
    public ResponseEntity<List<ContenidoDTO>> getContenidoByAnyo(@PathVariable int anyo){
        List<Contenido> contenidos = contenidoService.getContenidoByAnyo(anyo);
        List<ContenidoDTO> contenidosDTO = contenidoMapper.toDTO(contenidos);
        return new ResponseEntity<>(contenidosDTO, HttpStatus.OK);
    }

    @GetMapping("/contenidosByCategoria")
    public ResponseEntity<List<ContenidoDTO>> getContenidoByCategoria(@RequestParam String categoria){
        List<Contenido> contenidos = contenidoService.getContenidoByCategoria(categoria);
        List<ContenidoDTO> contenidosDTO = contenidoMapper.toDTO(contenidos);
        return new ResponseEntity<>(contenidosDTO, HttpStatus.OK);
    }

    @GetMapping("/contenidosByAutor")
    public ResponseEntity<List<ContenidoDTO>> getContenidoByAutorIsLikeIgnoreCase(@RequestParam String autor){
        List<Contenido> contenidos = contenidoService.getContenidoByAutorIsLikeIgnoreCase(autor);
        List<ContenidoDTO> contenidosDTO = contenidoMapper.toDTO(contenidos);
        return new ResponseEntity<>(contenidosDTO, HttpStatus.OK);
    }

    @GetMapping("/contenidosByGenero")
    public ResponseEntity<List<ContenidoDTO>> getContenidoByGeneroIsLikeIgnoreCase(@RequestParam String genero){
        List<Contenido> contenidos = contenidoService.getContenidoByGeneroIsLikeIgnoreCase(genero);
        List<ContenidoDTO> contenidosDTO = contenidoMapper.toDTO(contenidos);
        return new ResponseEntity<>(contenidosDTO, HttpStatus.OK);
    }

    @GetMapping("/contenidosByPuntuacion/{puntuacion}")
    public ResponseEntity<List<ContenidoDTO>> getContenidosByPuntuacionIsGreaterThanEqual(@PathVariable  float puntuacion){
        List<Contenido> contenidos = contenidoService.getContenidosByPuntuacionIsGreaterThanEqual(puntuacion);
        List<ContenidoDTO> contenidosDTO = contenidoMapper.toDTO(contenidos);
        return new ResponseEntity<>(contenidosDTO, HttpStatus.OK);
    }

    @GetMapping("/contenidoByTitulo/{titulo}")
    public ResponseEntity<ContenidoDTO> getContenidoByTitulo(@PathVariable String titulo){
        Contenido contenido = contenidoService.getContenidoByTitulo(titulo);
        ContenidoDTO contenidoDTO = contenidoMapper.toDto(contenido);
        return new ResponseEntity<>(contenidoDTO, HttpStatus.OK);
    }



}
