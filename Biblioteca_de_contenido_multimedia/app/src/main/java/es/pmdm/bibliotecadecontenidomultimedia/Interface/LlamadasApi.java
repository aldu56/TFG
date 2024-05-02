package es.pmdm.bibliotecadecontenidomultimedia.Interface;

import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.Model.ContenidoGuardado;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface  LlamadasApi {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("createUser")
    Call<User> createUser(@Body UserDto userDto);

    @GET("contenidosByCategoria")
    Call<List<Contenido>> getContenidoByCategoria(String categoria);

    @GET("contenidosGuardadosByUserId")
    Call<List<ContenidoGuardado>> getContenidosGuardadosByUserId(int userId);

    @GET("contenidos")
    Call<List<Contenido>> getContenidos();

}
