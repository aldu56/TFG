package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Datos_Multimedia extends AppCompatActivity {

    TextView textViewTitulo;
    TextView textViewDirector;
    TextView textViewAno;
    TextView txtGen;
    TextView textViewDuracion;
    TextView textViewDescripcion;
    EditText edComent;
    TextView txtComent;
    Button buttonVisitar;
    Button buttonAceptar;
    FloatingActionButton buttonVolverALaPrincipal;
    Button buttonGuardar;
    RatingBar ratingBar;

    ApiManager apiManager;
    ContenidoDto contenidoDto;
    ArrayList<ContenidoDto> contenidosUsuario =  new ArrayList<ContenidoDto>();
    UserDto user;
    String titulo;
    ContenidoDto contenido;

    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_multimedia);

        textViewTitulo = (TextView) findViewById(R.id.textViewTitulo);
        textViewDirector = (TextView) findViewById(R.id.textViewDirector);
        textViewAno = (TextView) findViewById(R.id.textViewAno);
        txtGen = (TextView) findViewById(R.id.txtGen);
        textViewDuracion = (TextView) findViewById(R.id.textViewDuracion);
        textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcion);
        edComent = (EditText) findViewById(R.id.edComent);
        txtComent = (TextView) findViewById(R.id.txtComent);
        buttonVisitar = (Button) findViewById(R.id.buttonVisitar);
        buttonAceptar = (Button) findViewById(R.id.buttonAceptar);
        buttonVolverALaPrincipal = (FloatingActionButton) findViewById(R.id.buttonVolverALaPrincipal);
        buttonGuardar = (Button) findViewById(R.id.buttonGuardar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        apiManager = new ApiManager();

        int idUser;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                titulo = null;
            } else {
                titulo = extras.getString("TITULO");
                idUser = getIntent().getExtras().getInt("ID_USER");
                token = getIntent().getStringExtra("TOKEN");
                System.out.println(idUser + " ID DEL USUARIO");
                System.out.println(titulo + "TITULO DEL CONTENIDO");
                obtenerListaUsuario(token, idUser);
            }
        }

        buttonVisitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contenido.getUrl()));
                startActivity(intent);
            }
        });

        buttonVolverALaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void obtenerListaUsuario(String token, int userId) {

        apiManager.getUserById(token, userId, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Datos_Multimedia.this, "Codigo (Listado_Multimedia) :  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                user = response.body();
                if (user != null) {
                    for (ContenidoDto con:
                         user.getContenidos()) {
                        System.out.println(con.toString());
                        contenidosUsuario.add(con);
                    }
                    montarDatosContenido();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(Datos_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void montarDatosContenido(){

        for (ContenidoDto c:
             contenidosUsuario) {
            if(c.getTitulo().toString().equalsIgnoreCase(titulo)){
                contenido = c;
            }
        }


        textViewTitulo.setText(contenido.getTitulo());
        textViewDirector.setText(contenido.getAutor());
        textViewAno.setText(String.valueOf(contenido.getAnyo()));
        txtGen.setText(contenido.getGenero());

        if(contenido.getCategoria().equalsIgnoreCase("pelicula") || contenido.getCategoria().equalsIgnoreCase("serie")){
            textViewDuracion.setText(contenido.getDuracion() + " minutos.");
        } else {
            textViewDuracion.setText(contenido.getDuracion() + " páginas.");

        }

        textViewDescripcion.setText(contenido.getDescripcion());

    }




}