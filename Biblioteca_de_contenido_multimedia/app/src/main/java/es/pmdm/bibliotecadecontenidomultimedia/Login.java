package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Interface.Llamada_api_1;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText edUsername;
    EditText edPassword;
    Button btnAcceder;
    Button btnRegistrarse;

    List<User> listaUsuarios = new ArrayList<>();
    int idUsuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = (EditText) findViewById(R.id.edUser);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnAcceder = (Button) findViewById(R.id.btnAcceder);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);


        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, password;

                username = edUsername.getText().toString();
                password = edPassword.getText().toString();

                if (!existeUser(username, password)) {
                    Toast.makeText(Login.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Login.this, Listado_Multimedia.class);
                    intent.putExtra("ID_USUARIO", idUsuario);
                    startActivity(intent);
                }
            }
        });


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


    }


    private boolean existeUser(String username, String password) {
        //Comprobar que existe el usuario antes de acceder y que coincide user con password.

        getUsers();

        Boolean bol = false;

        for (User us : listaUsuarios) {
            if (us.getUsername() == username && us.getPassword() == password) {
                idUsuario = us.getId();
                bol = true;
            } else {
                bol = false;
            }
        }

        return bol;
    }


    private void getUsers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.66:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Llamada_api_1 llamadaApi1 = retrofit.create(Llamada_api_1.class);

        Call<List<User>> call = llamadaApi1.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "Codigo:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listaUsuarios = response.body();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}