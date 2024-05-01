package com.example.API_biblioteca_multimedia.model.service.ContenidoGuardado;

import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;

import java.util.List;

public interface IContenidoGuardadoService {

    List<ContenidoGuardado> findAll();
    ContenidoGuardado findById(int id);
    ContenidoGuardado addContenidoGuardado(ContenidoGuardado contenidoGuardado);
    ContenidoGuardado updateContenidoGuardado(ContenidoGuardado contenidoGuardado);
    void deleteContenidoGuardado(ContenidoGuardado contenidoGuardado);

    List<ContenidoGuardado> getContenidoGuardadoByUserId(int id_user);
    List<ContenidoGuardado> getContenidoGuardadoByContenidoId(int id_contenido);

}
