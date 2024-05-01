package com.example.API_biblioteca_multimedia.model.repository;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IContenidoGuardadoRepository extends CrudRepository<ContenidoGuardado, Integer> {

    List<ContenidoGuardado> getContenidoGuardadoByUserId(int id_user);
    List<ContenidoGuardado> getContenidoGuardadoByContenidoId(int id_contenido);
}
