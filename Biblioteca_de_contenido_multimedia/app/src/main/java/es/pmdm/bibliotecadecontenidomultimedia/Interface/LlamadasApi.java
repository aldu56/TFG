package es.pmdm.bibliotecadecontenidomultimedia.Interface;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface  LlamadasApi {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("createUser")
    Call<User> createUser(@Body UserDto userDto);

    @GET("contenidosByCategoria")
    Call<List<Contenido>> getContenidoByCategoria(String categoria);

    @GET("contenidosNormales")
    Call<ArrayList<ContenidoDto>> getContenidos();

    @GET("userById/{id}")
    Call<UserDto> getUserById(@Path("id") int id);

    @PUT("updateUsers/{id}")
    Call<UserDto> updateUsers(@Path("id") int id, @Body UserDto userDto);

    @GET("/contenidoByTitulo/{titulo}")
    Call<ContenidoDto> getContenidoByTitulo(@Path("titulo") String titulo);
}
