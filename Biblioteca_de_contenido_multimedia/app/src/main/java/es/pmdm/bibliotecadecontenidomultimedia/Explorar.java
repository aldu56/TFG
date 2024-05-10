package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Explorar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar);

        //TODO Cuando clico en una pelicula de la lista, compruebo que no la tenga ya el user y la añado.
        // (Llamada api para ver lista user, y Llamada api para updatear user) Tambien Llamada api sacar todos los contenidos.
    }
}