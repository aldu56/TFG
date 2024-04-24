package com.example.API_biblioteca_multimedia.model.repository;

import com.example.API_biblioteca_multimedia.model.entity.Contenido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContenidoRepository extends CrudRepository<Contenido, Integer> {


    // Obtener Contenido por titulo.
    public Contenido getContenidoByTitulo(String titulo);

    // Obtener Contenido por Año.
    public List<Contenido> getContenidoByAnyo(int anyo);

    // Obtener Contenido por categoria (Libro, Pelicula, Cancion, Serie).
    public List<Contenido> getContenidoByCategoria(String categoria);

    // Obtener Contenido por Autor parecido a String.
    public List<Contenido> getContenidoByAutorIsLikeIgnoreCase(String autor);

    // Obtener Contenido por Genero parecido a String.
    public List<Contenido> getContenidoByGeneroIsLikeIgnoreCase(String genero);

    // Obtener Contenido por Puntuacion igual o superior a float.
    public List<Contenido> getContenidosByPuntuacionIsGreaterThanEqual(float puntuacion);
}
