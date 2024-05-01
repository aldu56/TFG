package es.pmdm.bibliotecadecontenidomultimedia.Model;

import java.io.Serializable;

public class Contenido implements Serializable {

    private int id;
    private String titulo;
    private String categoria;
    private int anyo;
    private String autor;
    private String genero;
    private String url;
    private String duracion;
    private String descripcion;
    private float puntuacion;
    private String comentario;


    public Contenido(int id, String titulo, String categoria, int anyo, String autor, String genero, String url, String duracion, String descripcion, float puntuacion, String comentario) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.anyo = anyo;
        this.autor = autor;
        this.genero = genero;
        this.url = url;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
}
