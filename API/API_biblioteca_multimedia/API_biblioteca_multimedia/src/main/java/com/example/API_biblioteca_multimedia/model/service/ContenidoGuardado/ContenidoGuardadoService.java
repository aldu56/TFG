package com.example.API_biblioteca_multimedia.model.service.ContenidoGuardado;

import com.example.API_biblioteca_multimedia.model.entity.ContenidoGuardado;
import com.example.API_biblioteca_multimedia.model.repository.IContenidoGuardadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenidoGuardadoService implements IContenidoGuardadoService{


    @Autowired
    public IContenidoGuardadoRepository iContenidoGuardadoRepository;
    @Override
    public List<ContenidoGuardado> findAll() {
        return (List<ContenidoGuardado>) iContenidoGuardadoRepository.findAll();
    }

    @Override
    public ContenidoGuardado findById(int id) {
        return iContenidoGuardadoRepository.findById(id).orElseThrow(null);
    }

    @Override
    public ContenidoGuardado addContenidoGuardado(ContenidoGuardado contenidoGuardado) {
        return iContenidoGuardadoRepository.save(contenidoGuardado);
    }

    @Override
    public ContenidoGuardado updateContenidoGuardado(ContenidoGuardado contenidoGuardado) {
        return null;
    }

    @Override
    public void deleteContenidoGuardado(ContenidoGuardado contenidoGuardado) {
        iContenidoGuardadoRepository.delete(contenidoGuardado);
    }

    @Override
    public List<ContenidoGuardado> getContenidoGuardadoByUserId(int id_user) {
        return iContenidoGuardadoRepository.getContenidoGuardadoByUserId(id_user);
    }

    @Override
    public List<ContenidoGuardado> getContenidoGuardadoByContenidoId(int id_contenido) {
        return iContenidoGuardadoRepository.getContenidoGuardadoByContenidoId(id_contenido);
    }
}
