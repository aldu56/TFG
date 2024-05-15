package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class About extends AppCompatActivity {

    ImageView imageView;
    Button buttonIrAlSitioWeb;
    Button buttonObtenerSoporte;
    Button buttonVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        // Asociar las variables con elementos de la interfaz de usuario
        buttonIrAlSitioWeb = (Button) findViewById(R.id.buttonIrAlSitioWeb);
        buttonObtenerSoporte = (Button) findViewById(R.id.buttonObtenerSoporte);
        buttonVolver = (Button) findViewById(R.id.buttonVolver);
        imageView = findViewById(R.id.imageView2);

        imageView.setImageResource(R.drawable.logo);



        buttonIrAlSitioWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/aldu56"));
                startActivity(intent);
            }
        });

        buttonObtenerSoporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Bibliotecade contenido multimedia");
                intent.putExtra(Intent.EXTRA_TEXT, "Texto del correo de soporte");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"albertoandu56@gmail.com"});
                startActivity(intent);
            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}