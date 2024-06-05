package com.example.API_biblioteca_multimedia.exceptions.Contenido;

public class ContenidoNotFoundException extends RuntimeException{

    public ContenidoNotFoundException(int id){
        super("Contenido no existente: " + id);
    }
    public ContenidoNotFoundException(String message) {
        super(message);
    }
}
