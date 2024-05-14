package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
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
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
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

    ArrayList<ContenidoDto> contenidosAuxiliar = new ArrayList<ContenidoDto>();

    UserDto user;
    int idUsuario;

    ArrayList<ContenidoDto> contenidoUsuarios = new ArrayList<>();
    ContenidoAdapter contenidoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multimedia);

       idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

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

                    ContenidoDto contenidoSeleccionado = contenidoAdapter.getItem(position);

                    if (contenidoSeleccionado != null) {
                        // Obtener el título del contenido seleccionado
                        String titulo = contenidoSeleccionado.getTitulo();

                        // Crear el intent y pasar el título como extra
                        Intent intent = new Intent(Listado_Multimedia.this, Datos_Multimedia.class);
                        intent.putExtra("TITULO", titulo);
                        intent.putExtra("ID_USER", idUsuario);
                        startActivity(intent);
                    }
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
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        // Obtiene el String de strings desde los recursos
        String sugerenciasBusqueda = getResources().getString(R.string.buscadorHint);

        // Asigna la sugerencia de búsqueda al SearchView
        searchView.setQueryHint(sugerenciasBusqueda);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contenidoAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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

    public void ordenarPorCategoria(ArrayList<ContenidoDto> contenidoUsuario, int id) {

        contenidoUsuarios.clear();
        contenidoUsuarios.addAll(contenidosAuxiliar);

        ArrayList<ContenidoDto> listaFiltrada = new ArrayList<>();

        // Filtrar contenidoUsuario según la categoría seleccionada
        for (ContenidoDto co : contenidoUsuario) {
            switch (id) {
                case 1:
                    if (co.getCategoria().equalsIgnoreCase("Pelicula")) {
                        listaFiltrada.add(co);
                    }
                    break;
                case 2:
                    if (co.getCategoria().equalsIgnoreCase("Serie")) {
                        listaFiltrada.add(co);
                    }
                    break;
                case 3:
                    if (co.getCategoria().equalsIgnoreCase("Libro")) {
                        listaFiltrada.add(co);
                    }
                    break;
                default:
                    break;
            }
        }

        // Si la lista filtrada no está vacía, actualiza el adaptador
        if (!listaFiltrada.isEmpty()) {
            contenidoAdapter.clear();
            contenidoAdapter.addAll(listaFiltrada);
            contenidoAdapter.notifyDataSetChanged();
        } else {
            // Si la lista filtrada está vacía, muestra un mensaje o realiza alguna acción
            Toast.makeText(this, "No se encontraron contenidos para esta categoría.", Toast.LENGTH_SHORT).show();
        }
    }


    public void obtenerListaUsuario(int userId) {

        apiManager.getUserById(userId, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Listado_Multimedia.this, "Codigo (Listado_Multimedia) :  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                user = response.body();
                if (user != null) {
                    contenidoUsuarios.clear();
                    contenidoUsuarios.addAll(user.getContenidos());
                    contenidosAuxiliar.addAll(user.getContenidos());
                    contenidoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(Listado_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        //TODO ARREGLAR
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final ContenidoDto contenido = (ContenidoDto) contenidoAdapter.getItem(info.position);

        int id = item.getItemId();

        if (id == R.id.eliminar) {
            montarUserUpdateado(contenido);
            updateUsuario(idUsuario, user);
        }
        return super.onContextItemSelected(item);
    }

    public void montarUserUpdateado(ContenidoDto contenido) {
        List<ContenidoDto> contenidosUsuarioCambiar = user.getContenidos();

        for (ContenidoDto c :
                contenidosUsuarioCambiar) {
            if (c.equals(contenido)) {
                contenidosUsuarioCambiar.remove(c);
            }
        }
        user.setContenidos(contenidosUsuarioCambiar);
    }

    public void updateUsuario(int id, UserDto userDto) {

        System.out.println(id + ": " + userDto.toString());
        apiManager.updateUsers(id, userDto, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Fallo actualizando el usuario" + response.toString());
                }else {
                    Toast.makeText(Listado_Multimedia.this, "Lista actualizada correctamente", Toast.LENGTH_SHORT).show();
                    obtenerListaUsuario(idUsuario);
                    contenidoAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                System.out.println("FALLO EN updateUsuario (Listado_Multimedia)" + t.getMessage().toString());
            }
        });
    }



}