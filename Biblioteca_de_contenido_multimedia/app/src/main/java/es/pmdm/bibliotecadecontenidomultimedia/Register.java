package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Interface.LlamadasApi;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText edUsername;
    EditText edPassword;
    Button btnRegistrarse;

    LlamadasApi llamadasApi;
    ApiManager apiManager;

    List<User> listaUsuarios = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();


        apiManager = new ApiManager();

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Rellena todos los campos.", Toast.LENGTH_SHORT).show();
                } else {

                    getUsersAndCheckUser(username, password);


                }
            }
        });
    }

    private void addUser(UserDto userDto) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.66:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        llamadasApi = retrofit.create(LlamadasApi.class);

        System.out.println(userDto);

        Call<User> call = llamadasApi.createUser(userDto);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }
                User user = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), "Usuario: '" + user.getUsername() + "' creado correctamente", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "a " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
                Log.e("Throw err: ", t.getMessage());
            }
        });


    }


    private void getUsersAndCheckUser(String username, String password) {
        apiManager.getUsers(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Register.this, "Codigo:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listaUsuarios = response.body();
                checkUser(username, password);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUser(String username, String password) {

        Boolean existe = false;
        if (listaUsuarios != null) {
            for (User us : listaUsuarios) {
                if (us.getUsername().equals(username)) {
                    existe = true;
                } else {

                }
            }

            if (existe == true) {
                Toast.makeText(this, "El usuario " + username + " ya existe.", Toast.LENGTH_SHORT).show();
            } else {
                UserDto userDto = new UserDto(username, password, null);

                addUser(userDto);
            }
        }
    }


}