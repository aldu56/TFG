package es.pmdm.bibliotecadecontenidomultimedia.Interface;

import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Llamada_api_1 {
    @GET("users")
    Call<List<User>> getUsers();
}
