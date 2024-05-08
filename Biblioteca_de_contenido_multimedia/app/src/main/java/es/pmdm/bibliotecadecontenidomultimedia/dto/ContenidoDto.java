package es.pmdm.bibliotecadecontenidomultimedia.dto;

import java.io.Serializable;

public class ContenidoDto implements Serializable {

    //  titulo, anyo, autor, genero, url, duracion, descripcion, puntuacion y comentario.

    private String titulo;

    private int anyo;

    private String autor;

    private String genero;

    private String url;

    private String duracion;

    private String descripcion;

    private float puntuacion;

    private String comentario;
    private String categoria;

    public ContenidoDto() {
    }

    public ContenidoDto(String titulo, int anyo, String autor, String genero, String url, String duracion, String descripcion, float puntuacion, String comentario, String categoria) {
        this.titulo = titulo;
        this.anyo = anyo;
        this.autor = autor;
        this.genero = genero;
        this.url = url;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ContenidoDto{" +
                "titulo='" + titulo + '\'' +
                ", anyo=" + anyo +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                ", url='" + url + '\'' +
                ", duracion='" + duracion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", puntuacion=" + puntuacion +
                ", comentario='" + comentario + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
