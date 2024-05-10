package es.pmdm.bibliotecadecontenidomultimedia;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Interface.LlamadasApi;
import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static final String BASE_URL = "http://192.168.1.64:8080/api/";
    private LlamadasApi llamadaApi1;

    public ApiManager() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        llamadaApi1 = retrofit.create(LlamadasApi.class);
    }

    public void getUsers(Callback<List<User>> callback) {
        Call<List<User>> call = llamadaApi1.getUsers();
        call.enqueue(callback);
    }


    public void getContenidoByCategoria(String categoria, Callback<List<Contenido>> callback){
        Call<List<Contenido>> call = llamadaApi1.getContenidoByCategoria(categoria);
        call.enqueue(callback);
    }

    public void getContenidos(Callback<List<Contenido>> callback){
        Call<List<Contenido>> call = llamadaApi1.getContenidos();
        call.enqueue(callback);
    }

    public void getUserById(int idUser, Callback<UserDto> callback){
        Call<UserDto> call = llamadaApi1.getUserById(idUser);
        call.enqueue(callback);
    }

    public void updateUsers(int id, UserDto userDto, Callback<UserDto> callback){
        Call<UserDto> call = llamadaApi1.updateUsers(id, userDto);
        call.enqueue(callback);
    }

    public void getContenidoByTitulo(String titulo, Callback<ContenidoDto> callback){
        Call<ContenidoDto> call = llamadaApi1.getContenidoByTitulo(titulo);
        call.enqueue(callback);
    }
}
