package es.pmdm.bibliotecadecontenidomultimedia;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Interface.LlamadasApi;
import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.LoginUserDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.RegisterUserDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UpdateUserDTO;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static final String BASE_URL = "http://192.168.1.66:8080/api/";
    private static final String BASE_AUTH_URL = "http://192.168.1.66:8080/auth/";
    private LlamadasApi llamadaApi;
    private LlamadasApi llamadaApiAuth;

    public ApiManager() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        llamadaApi = retrofit.create(LlamadasApi.class);

        Retrofit retrofitAuth = new Retrofit.Builder()
                .baseUrl(BASE_AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        llamadaApiAuth = retrofitAuth.create(LlamadasApi.class);

    }



    public void getUsers(String token, Callback<List<User>> callback) {
        Call<List<User>> call = llamadaApi.getUsers(token);
        call.enqueue(callback);
    }

    public void createUser(RegisterUserDto registerUserDto, Callback<User> callback) {
        Call<User> call = llamadaApiAuth.createUser(registerUserDto);
        call.enqueue(callback);
    }

    public void login(LoginUserDto loginUserDto, Callback<String> callback) {
        Call<String> call = llamadaApiAuth.login(loginUserDto);
        call.enqueue(callback);
    }




    public void getContenidos(String token, Callback<ArrayList<ContenidoDto>> callback){
        Call<ArrayList<ContenidoDto>> call = llamadaApi.getContenidos(token);
        call.enqueue(callback);
    }

    public void getUserById(String token, int idUser, Callback<UserDto> callback){
        Call<UserDto> call = llamadaApi.getUserById(token, idUser);
        call.enqueue(callback);
    }

    public void updateUsers(String token, int id, UserDto userDto, Callback<UserDto> callback){
        Call<UserDto> call = llamadaApi.updateUsers(token, id, userDto);
        call.enqueue(callback);
    }

    public void updateUser(String token, int id, UpdateUserDTO updateUserDTO, Callback<UserDto> callback){
        Call<UserDto> call = llamadaApi.updateUser(token, id, updateUserDTO);
        call.enqueue(callback);
    }

    public void getContenidoByTitulo(String token, String titulo, Callback<ContenidoDto> callback){
        Call<ContenidoDto> call = llamadaApi.getContenidoByTitulo(token, titulo);
        call.enqueue(callback);
    }
}
