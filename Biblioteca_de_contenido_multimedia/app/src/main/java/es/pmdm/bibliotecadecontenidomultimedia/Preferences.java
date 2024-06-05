package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Preferences extends AppCompatActivity {
    private RadioGroup rGroup;
    private RadioGroup rGroupF;
    RadioButton rbRojo;
    RadioButton rbAzul;
    RadioButton rbVerde;
    RadioButton rbBlanco;

    RadioButton rbRojoF;
    RadioButton rbAzulF;
    RadioButton rbVerdeF;
    RadioButton rbBlancoF;

    private EditText etSize;

    private Button btnGuardar;


    private SharedPreferences sharedPreferences;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        rGroup = findViewById(R.id.rGroup);

        rbAzul = findViewById(R.id.rbAzul);
        rbVerde = findViewById(R.id.rbAmarillo);
        rbRojo = findViewById(R.id.rbRojo);
        rbBlanco = findViewById(R.id.rbBlanco);

        rGroupF = findViewById(R.id.rGroupF);
        rbAzulF = findViewById(R.id.rbAzulF);
        rbVerdeF = findViewById(R.id.rbAmarilloF);
        rbRojoF = findViewById(R.id.rbRojoF);
        rbBlancoF = findViewById(R.id.rbBlancoF);




        etSize = findViewById(R.id.etSize);
        btnGuardar = findViewById(R.id.btnGuardar);

        getSupportActionBar().setTitle("Preferencias");


        sharedPreferences = getSharedPreferences("Preferencias", MODE_PRIVATE);


        if (sharedPreferences.contains("numero")) {
            int numeroGuardado = sharedPreferences.getInt("numero", 0);
            etSize.setText(String.valueOf(numeroGuardado));
        }


        if (sharedPreferences.contains("color")) {
            int colorGuardado = sharedPreferences.getInt("color", -1);
            if (colorGuardado == Color.RED) {
                rbRojo.setChecked(true);
            } else if (colorGuardado == Color.GREEN) {
                rbVerde.setChecked(true);
            } else if (colorGuardado == Color.BLUE) {
                rbAzul.setChecked(true);
            }
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPreferencias();
            }
        });
    }

    private void guardarPreferencias() {
        int numero;
        try {
            numero = Integer.parseInt(etSize.getText().toString());
            if (numero < 15 || numero > 30) {
                Toast.makeText(Preferences.this, "El tamaño debe estar entre 15dp y 30dp", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(Preferences.this, "Ingrese un número válido (15 - 30 dp)", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editorPreferencias = sharedPreferences.edit();
        editorPreferencias.putInt("numero", numero);

        int colorSeleccionado = rGroup.getCheckedRadioButtonId();
        if (colorSeleccionado == rbRojo.getId()) {
            editorPreferencias.putInt("color", Color.RED);
        } else if (colorSeleccionado == rbAzul.getId()) {
            editorPreferencias.putInt("color", Color.BLUE);
        } else if (colorSeleccionado == rbVerde.getId()) {
            editorPreferencias.putInt("color", Color.YELLOW);
        } else if (colorSeleccionado == rbBlanco.getId()) {
        editorPreferencias.putInt("color", Color.WHITE);



        //TODO ARREGLAR
        int fondo = rGroupF.getCheckedRadioButtonId();
        if (fondo == rbRojoF.getId()) {
            editorPreferencias.putInt("fondo", Color.RED);
        }
        else if (fondo == rbAzulF.getId()) {
            editorPreferencias.putInt("fondo", Color.BLUE);
        }
        else if (fondo == rbVerdeF.getId()) {
            editorPreferencias.putInt("fondo", Color.YELLOW);
        }
        else if (fondo == rbBlancoF.getId()) {
            editorPreferencias.putInt("fondo", Color.WHITE);
        }





    }

        editorPreferencias.apply();
        Intent intent = new Intent(Preferences.this, PoliticaPrivacidad.class);
        startActivity(intent);
    }
}