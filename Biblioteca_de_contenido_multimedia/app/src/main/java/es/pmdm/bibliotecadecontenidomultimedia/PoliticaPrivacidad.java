package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PoliticaPrivacidad extends AppCompatActivity {
    private TextView txtView;

    private SharedPreferences sharedPreferences;
    private ConstraintLayout constraintLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica_privacidad);

        getSupportActionBar().setTitle("Politica de Privacidad");


        txtView = findViewById(R.id.textView);
        constraintLayout = findViewById(R.id.constraintLayout);
        sharedPreferences = getSharedPreferences("Preferencias", MODE_PRIVATE);



        inicializarVisualizacion();
    }

    private void inicializarVisualizacion() {
        int tamanoPreferencia = obtenerTamanoPreferencia();
        int colorPreferencia = obtenerColorPreferencia();
        int colorFondo = obtenerColorFondo();

        txtView.setTextSize(tamanoPreferencia);
        txtView.setTextColor(colorPreferencia);
        constraintLayout.setBackgroundColor(colorFondo);

        mostrarTextoLorem();
    }

    private int obtenerTamanoPreferencia() {
        return sharedPreferences.getInt("numero", 0);
    }

    private int obtenerColorPreferencia() {
        return sharedPreferences.getInt("color", Color.BLACK);
    }

    private int obtenerColorFondo() {
        return sharedPreferences.getInt("fondo", Color.BLACK);
    }

    private void mostrarTextoLorem() {
        StringBuilder loremTexto = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.politicaprivacidad);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String linea;
            while ((linea = br.readLine()) != null) {
                loremTexto.append(linea).append("\n");
            }
            br.close();
            txtView.setText(loremTexto.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}