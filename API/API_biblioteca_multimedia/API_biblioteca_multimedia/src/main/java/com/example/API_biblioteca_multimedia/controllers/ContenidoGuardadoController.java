package com.example.API_biblioteca_multimedia.controllers;

import com.example.API_biblioteca_multimedia.dto.contenidoGuardado.ContenidoGuardadoDTO;
import com.example.API_biblioteca_multimedia.exceptions.Response;
import com.example.API_biblioteca_multimedia.mappers.ContenidoGuardado.ContenidoGuardadoMapper;
import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;
import com.example.API_biblioteca_multimedia.model.service.ContenidoGuardado.IContenidoGuardadoService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Contenido Guardado", description = "Gestión de contenido guardado por usuarios")
public class ContenidoGuardadoController {

    @Autowired
    private IContenidoGuardadoService contenidoGuardadoService;

    @Autowired
    ContenidoGuardadoMapper contenidoGuardadoMapper;

    private final Logger logger = LoggerFactory.getLogger(ContenidoGuardadoController.class);

    @GetMapping("/contenidosGuardados")
    public ResponseEntity<List<ContenidoGuardadoDTO>> getContenidosGuardados(){
        List<ContenidoGuardado> contenidosGuardados = contenidoGuardadoService.findAll();
        List<ContenidoGuardadoDTO> contenidosGuardadosDTO = contenidoGuardadoMapper.toDTO(contenidosGuardados);
        return new ResponseEntity<>(contenidosGuardadosDTO, HttpStatus.OK);
    }

    @PostMapping("/contenidosGuardados")
    public ResponseEntity<ContenidoGuardadoDTO> addContenidoGuardado(@RequestBody ContenidoGuardadoDTO contenidoGuardadoDTO){
        ContenidoGuardado contenidoGuardado = contenidoGuardadoMapper.fromDTO(contenidoGuardadoDTO);
        contenidoGuardado = contenidoGuardadoService.addContenidoGuardado(contenidoGuardado);
        ContenidoGuardadoDTO responseDTO = contenidoGuardadoMapper.toDTO(contenidoGuardado);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/contenidosGuardados/{id}")
    public ResponseEntity<Response> deleteContenidoGuardado(@PathVariable int id){
        ContenidoGuardado contenidoGuardado = contenidoGuardadoService.findById(id);
        contenidoGuardadoService.deleteContenidoGuardado(contenidoGuardado);
        return new ResponseEntity<>(Response.noErrorResponse("PeliculaGuardada borrada correctamente"), HttpStatus.OK);
    }

    @GetMapping("/contenidosGuardadosByUserId/{userId}")
    public ResponseEntity<List<ContenidoGuardadoDTO>> getContenidosGuardadosByUserId(@PathVariable int userId){
        List<ContenidoGuardado> contenidosGuardados = contenidoGuardadoService.getContenidoGuardadoByUserId(userId);
        List<ContenidoGuardadoDTO> contenidosGuardadosDTO = contenidoGuardadoMapper.toDTO(contenidosGuardados);
        return new ResponseEntity<>(contenidosGuardadosDTO, HttpStatus.OK);
    }

    @GetMapping("/contenidosGuardadosByContenidoId/{contenidoId}")
    public ResponseEntity<List<ContenidoGuardadoDTO>> getContenidosGuardadosByContenidoId(@PathVariable int contenidoId){
        List<ContenidoGuardado> contenidosGuardados = contenidoGuardadoService.getContenidoGuardadoByContenidoId(contenidoId);
        List<ContenidoGuardadoDTO> contenidosGuardadosDTO = contenidoGuardadoMapper.toDTO(contenidosGuardados);
        return new ResponseEntity<>(contenidosGuardadosDTO, HttpStatus.OK);
    }

}
