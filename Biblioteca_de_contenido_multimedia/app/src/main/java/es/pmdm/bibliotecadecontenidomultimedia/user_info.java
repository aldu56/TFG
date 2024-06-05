package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class user_info extends AppCompatActivity {
    TextView txtUsername;
    TextView txtContenidos;
    TextView txtPelis;
    TextView txtSeries;
    TextView txtLibros;
    ApiManager apiManager = new ApiManager();
    UserDto user = new UserDto();
    int idUsuario;
    FloatingActionButton btnVolver;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);
        token = getIntent().getStringExtra("TOKEN");

        getSupportActionBar().setTitle("User Info");


        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtContenidos = (TextView) findViewById(R.id.txtContenidos);
        txtPelis = (TextView) findViewById(R.id.txtPelis);
        txtSeries = (TextView) findViewById(R.id.txtSeries);
        txtLibros = (TextView) findViewById(R.id.txtLibros);
        btnVolver = (FloatingActionButton) findViewById(R.id.buttonVolverAtras);

        obtenerListaUsuario(token, idUsuario);




        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.preferencesitem){
            Intent intent = new Intent(user_info.this, Preferences.class);
            intent.putExtra("ID_USER", idUsuario);
            startActivityForResult(intent, -10);
        }else if(item.getItemId() == R.id.cambiarPasswordMenu){
            Intent intent = new Intent(user_info.this, change_pass.class);
            intent.putExtra("ID_USUARIO", idUsuario);
            intent.putExtra("TOKEN", token);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void obtenerListaUsuario(String token, int userId) {
        apiManager.getUserById(token, userId, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(user_info.this, "Codigo (Listado_Multimedia) :  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                user = response.body();
                if (user != null) {
                    asignarTextos();
                }

            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(user_info.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void asignarTextos() {
        List<ContenidoDto> contenidoDtos = new ArrayList<ContenidoDto>();

        for (ContenidoDto contenidoDto :
                user.getContenidos()) {
            contenidoDtos.add(contenidoDto);

        }

        int numeroContenidos = contenidoDtos.size();
        int numeroPelis = 0;
        int numeroSeries = 0;
        int numeroLibros = 0;
        for (ContenidoDto contenidoDto :
                contenidoDtos) {
            if (contenidoDto.getCategoria().equalsIgnoreCase("Pelicula")) {
                numeroPelis++;
            }
        }
        for (ContenidoDto contenidoDto :
                contenidoDtos) {
            if (contenidoDto.getCategoria().equalsIgnoreCase("Serie")) {
                numeroSeries++;
            }
        }
        for (ContenidoDto contenidoDto :
                contenidoDtos) {
            if (contenidoDto.getCategoria().equalsIgnoreCase("Libro")) {
                numeroLibros++;
            }
        }

        txtUsername.setText(user.getUsername());
        txtContenidos.setText(String.valueOf(numeroContenidos));
        txtPelis.setText(String.valueOf(numeroPelis));
        txtSeries.setText(String.valueOf(numeroSeries));
        txtLibros.setText(String.valueOf(numeroLibros));


    }
}