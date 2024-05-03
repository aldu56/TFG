package es.pmdm.bibliotecadecontenidomultimedia.Model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private int id;

    private String username;
    private String password;

   private List<Contenido> contenidos;

    public User() {
    }

    public User(int id, String username, String password, List<Contenido> contenidos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contenidos = contenidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }
}
