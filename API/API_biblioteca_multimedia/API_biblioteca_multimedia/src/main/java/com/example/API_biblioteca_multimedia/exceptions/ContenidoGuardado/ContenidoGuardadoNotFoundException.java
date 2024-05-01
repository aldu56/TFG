package com.example.API_biblioteca_multimedia.exceptions.ContenidoGuardado;

public class ContenidoGuardadoNotFoundException extends RuntimeException{

    public ContenidoGuardadoNotFoundException(int id){
        super("ContenidoGuardado no existente: " + id);
    }
    public ContenidoGuardadoNotFoundException(String message) {
        super(message);
    }
}
