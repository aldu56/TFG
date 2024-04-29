package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {


    EditText edUsername;
    EditText edPassword;
    Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edUsername.getText().length() == 0 || edPassword.getText().length() == 0 ){
                    Toast.makeText(Register.this, "Rellena todos los campos.", Toast.LENGTH_SHORT).show();
                }else{
                    addUser(edUsername, edPassword);
                }
            }
        });
    }


    private void addUser(EditText edUsername, EditText edPassword) {
        if (!existeUser(edUsername)) {
            // TODO CREAR EL USUARIO (llamar a api y pasarle los datos a añadir)


            finish(); // Vuelvo al activity Login una vez se ha creado el usuario
        }else{
            Toast.makeText(this, "El nombre de usuario no esta disponible.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean existeUser(EditText edUsername) {
        if(true){ //TODO Comparar el edUsername con la lista de usuarios de la API para ver si ya existe el user
            return false;
        }else{
            return true;
        }
    }


}