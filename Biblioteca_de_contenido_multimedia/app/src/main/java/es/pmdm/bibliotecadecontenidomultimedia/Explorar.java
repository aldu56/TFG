package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Explorar extends AppCompatActivity {


    ListView listView;
    private ArrayList<ContenidoDto> listaGenerica;
    private ArrayList<ContenidoDto> listaContenidos = new ArrayList<ContenidoDto>();
    private ArrayList<ContenidoDto> listaContenidosAuxiliar = new ArrayList<ContenidoDto>();

    ContenidoAdapter contenidoAdapter;

    ApiManager apiManager =  new ApiManager();
    int idUsuario;
    UserDto user;
    ArrayList<ContenidoDto> contenidoUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar);
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);


        listView = (ListView) findViewById(R.id.listViewEx);

        obtenerListaGenerica();
        obtenerListaUsuario(idUsuario);

        apiManager = new ApiManager();
        contenidoAdapter = new ContenidoAdapter(this, R.layout.item_view, listaContenidos);
        listView.setAdapter(contenidoAdapter);

        contenidoAdapter.notifyDataSetChanged();


        registerForContextMenu(listView);


        //TODO Cuando clico en una pelicula de la lista, compruebo que no la tenga ya el user y la añado.
        // (Llamada api para ver lista user, y Llamada api para updatear user) Tambien Llamada api sacar todos los contenidos.


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContenidoDto contenidoSeleccionado = contenidoAdapter.getItem(position);
                montarUserUpdateado(contenidoSeleccionado);
                updateUsuario(idUsuario, user);
            }
        });
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
                ordenarPorCategoria(listaContenidos, 1);
                break;
            case R.id.filtroSerie:
                ordenarPorCategoria(listaContenidos, 2);
                break;
            case R.id.filtroLibro:
                ordenarPorCategoria(listaContenidos, 3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ordenarPorCategoria(ArrayList<ContenidoDto> contenidoUsuario, int id) {

        listaContenidos.clear();
        listaContenidos.addAll(listaContenidosAuxiliar);

        ArrayList<ContenidoDto> listaFiltrada = new ArrayList<>();

        // Filtrar lista según la categoría seleccionada
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

    public void obtenerListaGenerica() {
        apiManager.getContenidos(new Callback<ArrayList<ContenidoDto>>() {
            @Override
            public void onResponse(Call<ArrayList<ContenidoDto>> call, Response<ArrayList<ContenidoDto>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Explorar.this, "Codigo:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listaGenerica = response.body();
                if (listaGenerica != null) {
                    listaContenidos.clear();
                    listaContenidos.addAll(listaGenerica);
                    listaContenidosAuxiliar.addAll(listaGenerica);
                    contenidoAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContenidoDto>> call, Throwable t) {
                Toast.makeText(Explorar.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerListaUsuario(int userId) {

        apiManager.getUserById(userId, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Explorar.this, "Codigo (Explorar) :  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                user = response.body();
                if (user != null) {
                    contenidoUsuarios.clear();
                    contenidoUsuarios.addAll(user.getContenidos());
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(Explorar.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void montarUserUpdateado(ContenidoDto contenido) {

        ArrayList<ContenidoDto> listaActualizada = new ArrayList<ContenidoDto>();
        listaActualizada.addAll(contenidoUsuarios);
        listaActualizada.add(contenido);

        user.setContenidos(listaActualizada);


    }

    public void updateUsuario(int id, UserDto userDto) {
//TODO REVISAR
        System.out.println("ACTUALIZAR USER EXPLORAR: " + id + ": " + userDto.toString());
        apiManager.updateUsers(id, userDto, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Fallo actualizando el usuario (Explorar)" + response.toString());
                } else {
                    Toast.makeText(Explorar.this, "El contenido se ha añadido correctamente. ", Toast.LENGTH_SHORT).show();
                    obtenerListaUsuario(idUsuario);
                }

            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                System.err.println("FALLO EN updateUsuario (Explorar)" + t.getMessage().toString());
            }
        });
    }
}