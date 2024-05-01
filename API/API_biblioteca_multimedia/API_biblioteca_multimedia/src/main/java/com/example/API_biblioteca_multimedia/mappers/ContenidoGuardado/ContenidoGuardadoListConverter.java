package com.example.API_biblioteca_multimedia.mappers.ContenidoGuardado;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ContenidoGuardadoListConverter extends AbstractConverter<List<ContenidoGuardado>, List<Integer>> {
    @Override
    protected List<Integer> convert(List<ContenidoGuardado> contenidoGuardados) {
        return contenidoGuardados.stream().map(ContenidoGuardado::getId).collect(Collectors.toList());
    }
}
