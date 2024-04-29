package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText edUsername;
    EditText edPassword;
    Button btnAcceder;
    Button btnRegistrarse;

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

                if(!existeUser(username, password)){
                    Toast.makeText(Login.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }else{

                    int idUsuario = 0; // TODO Almacenar id del usuario para posteriormente poder asignarle contenido a su lista.


                    Intent intent =  new Intent(Login.this, Listado_Multimedia.class);
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


    private boolean existeUser(String username, String password){
    // TODO Comprobar que existe el usuario antes de acceder y que coincide user con password.
        if(true){ //TODO Comparar el edUsername con la lista de usuarios de la API y el password con el password del user(API).
            return false;
        }else{
            return true;
        }

    }
}