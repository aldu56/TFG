package com.example.API_biblioteca_multimedia.model.service.Contenido;

import com.example.API_biblioteca_multimedia.exceptions.Contenido.ContenidoNotFoundException;
import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import com.example.API_biblioteca_multimedia.model.repository.IContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenidoService implements IContenidoService{

    @Autowired
    private IContenidoRepository contenidoRepository;


    @Override
    public List<Contenido> findAll() {
        return (List<Contenido>) contenidoRepository.findAll();
    }

    @Override
    public Contenido findById(int id) {
        return contenidoRepository.findById(id).orElseThrow(() -> new ContenidoNotFoundException("Error al buscar el contenido con ID: " + id));
    }

    @Override
    public Contenido addContenido(Contenido contenido) {
        return contenidoRepository.save(contenido);
    }

    @Override
    public Contenido updateContenido(Contenido contenido) {
        return null;
    }

    @Override
    public void deleteContenido(Contenido contenido) {
        contenidoRepository.delete(contenido);
    }

    @Override
    public Contenido getContenidoByTitulo(String titulo) {
        return contenidoRepository.getContenidoByTitulo(titulo);
    }

    @Override
    public List<Contenido> getContenidoByAnyo(int anyo) {
        return contenidoRepository.getContenidoByAnyo(anyo);
    }

    @Override
    public List<Contenido> getContenidoByCategoria(String categoria) {
        return contenidoRepository.getContenidoByCategoria(categoria);
    }

    @Override
    public List<Contenido> getContenidoByAutorIsLikeIgnoreCase(String autor) {
        return contenidoRepository.getContenidoByAutorIsLikeIgnoreCase(autor);
    }

    @Override
    public List<Contenido> getContenidoByGeneroIsLikeIgnoreCase(String genero) {
        return contenidoRepository.getContenidoByGeneroIsLikeIgnoreCase(genero);
    }

    @Override
    public List<Contenido> getContenidosByPuntuacionIsGreaterThanEqual(float puntuacion) {
        return contenidoRepository.getContenidosByPuntuacionIsGreaterThanEqual(puntuacion);
    }
}
