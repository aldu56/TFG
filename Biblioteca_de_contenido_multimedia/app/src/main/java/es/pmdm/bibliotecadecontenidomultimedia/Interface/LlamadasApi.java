package es.pmdm.bibliotecadecontenidomultimedia.Interface;

import android.media.session.MediaSession;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.LoginUserDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.RegisterUserDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UpdateUserDTO;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface  LlamadasApi {
    @GET("users")
    Call<List<User>> getUsers(@Header("Authorization")String token);

    @POST("signup")
    Call<User> createUser(@Body RegisterUserDto RegisterUserDto);

    @POST("login")
    Call<String> login(@Body LoginUserDto loginUserDto);



    @GET("contenidosNormales")
    Call<ArrayList<ContenidoDto>> getContenidos(@Header("Authorization")String token);

    @GET("userById/{id}")
    Call<UserDto> getUserById(@Header("Authorization")String token, @Path("id") int id);

    @PUT("updateUsers/{id}")
    Call<UserDto> updateUsers(@Header("Authorization")String token, @Path("id") int id, @Body UserDto userDto);

    @PUT("updateUser/{id}")
    Call<UserDto> updateUser(@Header("Authorization")String token, @Path("id") int id, @Body UpdateUserDTO updateUserDTO);

    @GET("/contenidoByTitulo/{titulo}")
    Call<ContenidoDto> getContenidoByTitulo(@Header("Authorization")String token, @Path("titulo") String titulo);
}
