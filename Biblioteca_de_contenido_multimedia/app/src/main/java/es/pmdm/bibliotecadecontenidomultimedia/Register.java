package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Interface.LlamadasApi;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.dto.RegisterUserDto;
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

        getSupportActionBar().setTitle("Registrarse");


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

                    addUser(username, password);

                }
            }
        });
    }

    private void addUser(String username, String password) {

        List<Long> rolIds = new ArrayList<Long>();


            RegisterUserDto registerUserDto = new RegisterUserDto();
            registerUserDto.setUsername(username);
            registerUserDto.setPassword(password);
            registerUserDto.setRolIds(rolIds);

        apiManager.createUser(registerUserDto, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Register.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("Response err: ", response.message());
                    return;
                }
                User user = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), "Usuario: '" + username + "' creado correctamente", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Register.this, "Usuario ya existente.", Toast.LENGTH_SHORT).show();
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }
}