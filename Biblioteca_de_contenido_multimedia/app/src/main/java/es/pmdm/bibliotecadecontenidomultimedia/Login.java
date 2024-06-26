package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.ApiManager;
import es.pmdm.bibliotecadecontenidomultimedia.Listado_Multimedia;
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import es.pmdm.bibliotecadecontenidomultimedia.R;
import es.pmdm.bibliotecadecontenidomultimedia.Register;
import es.pmdm.bibliotecadecontenidomultimedia.dto.LoginUserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText edUsername;
    EditText edPassword;
    Button btnAcceder;
    Button btnRegistrarse;

    List<User> listaUsuarios = null;
    int idUsuario = 0;

    String token;

    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();


        apiManager = new ApiManager();

        edUsername = findViewById(R.id.edUser);
        edPassword = findViewById(R.id.edPassword);
        btnAcceder = findViewById(R.id.btnAcceder);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                getUsersAndCheckUser(username, password);
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

    private void getUsersAndCheckUser(String username, String password) {
        LoginUserDto loginUserDto = new LoginUserDto();

        loginUserDto.setUsername(username);
        loginUserDto.setPassword(password);



        apiManager.login(loginUserDto, new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "Codigo login:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                token = "Bearer " +  response.body();

                getUserId(username);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Login.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void getUserId(String username){
        apiManager.getUsers(token, new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "Codigo pollo:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(Login.this, "FUNCA", Toast.LENGTH_SHORT).show();

                listaUsuarios = response.body();

                // Verificar si la lista de usuarios no es nula y no está vacía
                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                    // Iterar a través de todos los usuarios y hacer la comprobación
                    for (User user : listaUsuarios) {
                        if (user.getUsername().equals(username)) {
                            idUsuario = user.getId();
                            System.out.println("Id Usuario " + user.getUsername() + ": " + user.getId());
                            Intent intent = new Intent(Login.this, Listado_Multimedia.class);
                            intent.putExtra("ID_USUARIO", idUsuario);
                            intent.putExtra("TOKEN", token);
                            startActivity(intent);
                            return;
                        }
                    }
                }

                // Si llegamos aquí, significa que no se encontró ningún usuario válido
                Toast.makeText(Login.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
