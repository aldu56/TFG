package com.example.API_biblioteca_multimedia.model.repository;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;
import org.springframework.data.repository.CrudRepository;

public interface IContenidoGuardadoRepository extends CrudRepository<ContenidoGuardado, Integer> {
}
