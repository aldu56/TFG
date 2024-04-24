package com.example.API_biblioteca_multimedia.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "peliculas_guardadas")
public class PeliculaGuardada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeliculaGuardada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contenido", referencedColumnName = "id")
    private Contenido contenido;

}
