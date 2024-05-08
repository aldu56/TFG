package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Listado_Multimedia extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    FloatingActionButton btnNueva;
    ApiManager apiManager;

    List<Contenido> listaGenerica;

    UserDto user;

    ArrayList<ContenidoDto> contenidoUsuarios = new ArrayList<>();
    ArrayList<ContenidoDto> listaPeliculas =  new ArrayList<>();
    ArrayList<ContenidoDto> listaSeries = new ArrayList<>();
    ArrayList<ContenidoDto> listaLibros = new ArrayList<>();

    ContenidoAdapter contenidoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multimedia);

        int idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

        if (idUsuario != -1) {
//            searchView = findViewById(R.id.search_bar);
            listView = (ListView) findViewById(R.id.listView);
            btnNueva = findViewById(R.id.btnNueva);
            apiManager = new ApiManager();



            obtenerListaUsuario(idUsuario);


            contenidoAdapter = new ContenidoAdapter(this, R.layout.item_view, contenidoUsuarios);


            listView.setAdapter(contenidoAdapter);
            contenidoAdapter.notifyDataSetChanged();

            registerForContextMenu(listView);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Listado_Multimedia.this, Datos_Multimedia.class);
                    intent.putExtra("POSICION", position);
                    startActivity(intent);
                }
            });





            btnNueva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Listado_Multimedia.this, Explorar.class);
                    intent.putExtra("ID_USUARIO", idUsuario);
                    Toast.makeText(Listado_Multimedia.this, "Explorar", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });


        } else {
            Toast.makeText(this, "El id del usuario no se recibió correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.filtroPeli:
                ordenarPorCategoria(contenidoUsuarios, 1);
                break;
            case R.id.filtroSerie:
                    ordenarPorCategoria(contenidoUsuarios, 2);
                break;
            case R.id.filtroLibro:
                    ordenarPorCategoria(contenidoUsuarios, 3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void obtenerListaUsuario(int userId) {
        apiManager.getUserById(userId, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Listado_Multimedia.this, "Codigo (Listado_Multimedia) :  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDto user = response.body();
                if (user != null) {
                    contenidoUsuarios.addAll(user.getContenidos());
                    contenidoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(Listado_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ordenarPorCategoria(ArrayList<ContenidoDto> contenidoUsuario, int id){

                for (ContenidoDto co :
                        contenidoUsuario) {
                    switch (id){
                        case 1:
                            if (co.getCategoria().equals(R.string.PELICULA)){
                                listaPeliculas.add(co);
                            }
                            break;
                        case 2:
                            if (co.getCategoria().equals(R.string.SERIE)){
                                listaSeries.add(co);
                            }
                            break;
                        case 3:
                            if (co.getCategoria().equals(R.string.LIBRO)){
                                listaLibros.add(co);
                            }
                            break;
                        default:
                            break;
                    }
                }
                switch (id){
                    case 1:
                        contenidoAdapter = new ContenidoAdapter(Listado_Multimedia.this, R.layout.item_view, listaPeliculas);
                        break;
                    case 2:
                        contenidoAdapter = new ContenidoAdapter(Listado_Multimedia.this, R.layout.item_view, listaSeries);
                        break;
                    case 3:
                        contenidoAdapter = new ContenidoAdapter(Listado_Multimedia.this, R.layout.item_view, listaLibros);
                        break;
                    default:
                        break;
                }
                contenidoAdapter.notifyDataSetChanged();

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
                if (listaGenerica != null) {
                    // Aquí puedes realizar cualquier manipulación adicional que necesites con la listaGenerica
                }
            }

            @Override
            public void onFailure(Call<List<Contenido>> call, Throwable t) {
                Toast.makeText(Listado_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}