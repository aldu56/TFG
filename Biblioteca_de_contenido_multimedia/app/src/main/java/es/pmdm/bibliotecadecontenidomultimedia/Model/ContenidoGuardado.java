package es.pmdm.bibliotecadecontenidomultimedia.Model;

public class ContenidoGuardado {
    private int id;
    private int id_user;
    private int id_contenido;

    public ContenidoGuardado() {
    }

    public ContenidoGuardado(int id, int id_user, int id_contenido) {
        this.id = id;
        this.id_user = id_user;
        this.id_contenido = id_contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }
}
