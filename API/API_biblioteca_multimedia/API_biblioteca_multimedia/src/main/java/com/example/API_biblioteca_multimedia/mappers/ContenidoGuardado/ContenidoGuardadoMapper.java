package com.example.API_biblioteca_multimedia.mappers.ContenidoGuardado;

import com.example.API_biblioteca_multimedia.dto.contenidoGuardado.ContenidoGuardadoDTO;
import com.example.API_biblioteca_multimedia.dto.contenidoGuardado.CreateContenidoGuardadoDTO;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ContenidoGuardadoMapper {

    @Autowired
    private ModelMapper modelMapper;


    public ContenidoGuardadoDTO toDTO(ContenidoGuardado contenidoGuardado) {
        return modelMapper.map(contenidoGuardado, ContenidoGuardadoDTO.class);
    }

    public ContenidoGuardado fromDTO(ContenidoGuardadoDTO contenidoGuardadoDTO)
    {
        return modelMapper.map(contenidoGuardadoDTO, ContenidoGuardado.class);
    }

    public List<ContenidoGuardadoDTO> toDTO(List<ContenidoGuardado> contenidoGuardados)
    {
        return contenidoGuardados.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ContenidoGuardado fromDTO(CreateContenidoGuardadoDTO contenidoGuardadoDTO)
    {
        return modelMapper.map(contenidoGuardadoDTO, ContenidoGuardado.class);
    }

}
