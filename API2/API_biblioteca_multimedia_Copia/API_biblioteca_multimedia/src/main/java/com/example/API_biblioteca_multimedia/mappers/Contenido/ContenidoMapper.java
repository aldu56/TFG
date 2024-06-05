package com.example.API_biblioteca_multimedia.mappers.Contenido;

import com.example.API_biblioteca_multimedia.dto.contenido.ContenidoDTO;
import com.example.API_biblioteca_multimedia.dto.contenido.UpdateContenidoDTO;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ContenidoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ContenidoDTO toDto(Contenido contenido) {
        return modelMapper.map(contenido, ContenidoDTO.class);
    }

    public List<ContenidoDTO> toDTO(List<Contenido> contenidos){
        return contenidos.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Contenido fromDTO(UpdateContenidoDTO contenidoDTO){
        return modelMapper.map(contenidoDTO, Contenido.class);
    }

}
