package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.Model.ContenidoGuardado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Listado_Multimedia extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView rvLista;
    ArrayList<Contenido> listaContenidoUsuario;
    SearchView txtBuscar;
    FloatingActionButton btnNueva;
    ApiManager apiManager;

    List<ContenidoGuardado> listaIds;
    List<Contenido> listaGenerica;

    ArrayList<Contenido> listaUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multimedia);
        int idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

        if (idUsuario != -1) {

        } else {
            Toast.makeText(this, "El id del usuario no se recibió correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        }

        txtBuscar = findViewById(R.id.txtBuscar);
        rvLista = findViewById(R.id.rvLista);
        btnNueva = findViewById(R.id.btnNueva);


        //Asigno el menu contextual a la lista para que al mantener me de la opcion de eliminar // TODO configurar el click en eliminar
        registerForContextMenu(rvLista);


        obtenerListaUsuario(idUsuario);
        obtenerListaGenerica();
        asignarLista(listaGenerica);

        ContenidoAdapter adapter = new ContenidoAdapter(listaUsuario);


        txtBuscar.setOnQueryTextListener(this); // Para que se busque en tiempo real.


        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Listado_Multimedia.this, Explorar.class);
                intent.putExtra("ID_USUARIO", idUsuario);
                Toast.makeText(Listado_Multimedia.this, "Explorar", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public void obtenerListaUsuario(int userId) {

        apiManager.getContenidosGuardadosByUserId(userId, new Callback<List<ContenidoGuardado>>() {
            @Override
            public void onResponse(Call<List<ContenidoGuardado>> call, Response<List<ContenidoGuardado>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Listado_Multimedia.this, "Codigo:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listaIds = response.body();
            }


            @Override
            public void onFailure(Call<List<ContenidoGuardado>> call, Throwable t) {

            }
        });

    }

    public void obtenerListaGenerica() {

        apiManager.getContenidos(new Callback<List<Contenido>>() {
            @Override
            public void onResponse(Call<List<Contenido>> call, Response<List<Contenido>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Listado_Multimedia.this, "Codigo:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listaGenerica = response.body();
            }

            @Override
            public void onFailure(Call<List<Contenido>> call, Throwable t) {

            }
        });
    }

    public void asignarLista(List<Contenido> listaGenerica) {

        for (Contenido con :
                listaGenerica) {
            for (ContenidoGuardado cg :
                    listaIds) {
                if (con.getId() == cg.getId_contenido()) {
                    listaUsuario.add(con);
                }

            }

        }


    }
}