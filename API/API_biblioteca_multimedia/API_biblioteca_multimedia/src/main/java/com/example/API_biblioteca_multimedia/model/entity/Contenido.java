package com.example.API_biblioteca_multimedia.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contenido")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private int anyo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String duracion;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private float puntuacion;

    @Column(nullable = false)
    private String comentario;

}
