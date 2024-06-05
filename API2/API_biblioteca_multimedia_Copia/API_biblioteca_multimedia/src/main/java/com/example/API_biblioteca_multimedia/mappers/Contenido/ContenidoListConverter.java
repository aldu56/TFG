package com.example.API_biblioteca_multimedia.mappers.Contenido;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ContenidoListConverter extends AbstractConverter<List<Contenido>, List<Integer>> {
    @Override
    protected List<Integer> convert(List<Contenido> contenidos) {
        return contenidos.stream().map(Contenido::getId).collect(Collectors.toList());
    }
}
