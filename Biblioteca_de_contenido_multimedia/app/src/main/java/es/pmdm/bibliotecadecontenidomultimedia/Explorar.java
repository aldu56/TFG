package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UpdateUserDTO;
import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Explorar extends AppCompatActivity {


    ListView listView;
    FloatingActionButton btnVolverExpl;
    private ArrayList<ContenidoDto> listaGenerica;
    private ArrayList<ContenidoDto> listaContenidos = new ArrayList<ContenidoDto>();
    private ArrayList<ContenidoDto> listaContenidosAuxiliar = new ArrayList<ContenidoDto>();

    ContenidoAdapter contenidoAdapter;

    ApiManager apiManager = new ApiManager();
    int idUsuario;
    UserDto user;
    ArrayList<ContenidoDto> contenidoUsuarios = new ArrayList<>();

    String token;

    UpdateUserDTO updateUserDTO = new UpdateUserDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar);
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);
        token = getIntent().getStringExtra("TOKEN");

        getSupportActionBar().setTitle("Explorar contenido");



        listView = (ListView) findViewById(R.id.listViewEx);
        btnVolverExpl = findViewById(R.id.btnVolverExp);

        obtenerListaGenerica();
        obtenerListaUsuario(token, idUsuario);

        apiManager = new ApiManager();
        contenidoAdapter = new ContenidoAdapter(this, R.layout.item_view, listaContenidos);
        listView.setAdapter(contenidoAdapter);

        contenidoAdapter.notifyDataSetChanged();


        registerForContextMenu(listView);



btnVolverExpl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContenidoDto contenidoSeleccionado = contenidoAdapter.getItem(position);
                boolean contenidoYaEnLista = false;


                for (ContenidoDto contenido : contenidoUsuarios) {
                    if (contenido.getTitulo().equalsIgnoreCase(contenidoSeleccionado.getTitulo())) {
                        contenidoYaEnLista = true;
                        break;
                    }
                }

                if (contenidoYaEnLista) {
                    Toast.makeText(Explorar.this, "El contenido ya está en tu lista", Toast.LENGTH_SHORT).show();
                } else {
                    montarUserUpdateado(contenidoSeleccionado);
                    updateUsuario(token, idUsuario, updateUserDTO);

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu2, menu);
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
                if (newText.isEmpty()) {
                    // Si el texto de búsqueda está vacío, restaurar la lista completa
                    contenidoAdapter.clear();
                    contenidoAdapter.addAll(listaContenidosAuxiliar);
                    contenidoAdapter.notifyDataSetChanged();
                } else {
                    // Aplicar filtro por título
                    contenidoAdapter.filterByTitle(newText);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Si no hay ningún elemento mostrándose, restaurar la lista completa
                if (contenidoAdapter.getCount() == 0) {
                    contenidoAdapter.clear();
                    contenidoAdapter.addAll(listaContenidosAuxiliar);
                    contenidoAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // No necesitamos realizar ninguna acción cuando el SearchView se expande
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Limpiar el texto del SearchView cuando se colapsa
                if (contenidoAdapter.getCount() == 0) {
                    searchView.setQuery("", false);
                    contenidoAdapter.clear();
                    contenidoAdapter.addAll(listaContenidosAuxiliar);
                    contenidoAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filtroPeli:
                Toast.makeText(this, "Filtrando por Peliculas.", Toast.LENGTH_SHORT).show();
                ordenarPorCategoria(listaContenidos, 1);
                return true;
            case R.id.filtroSerie:
                Toast.makeText(this, "Filtrando por Series.", Toast.LENGTH_SHORT).show();
                ordenarPorCategoria(listaContenidos, 2);
                return true;
            case R.id.filtroLibro:
                Toast.makeText(this, "Filtrando por Libros.", Toast.LENGTH_SHORT).show();
                ordenarPorCategoria(listaContenidos, 3);
                return true;
            case R.id.todos:
                Toast.makeText(this, "Mostrando todos los contenidos.", Toast.LENGTH_SHORT).show();
                ordenarPorCategoria(listaContenidos, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ordenarPorCategoria(ArrayList<ContenidoDto> contenidoUsuario, int id) {
        listaContenidos.clear();
        listaContenidos.addAll(listaContenidosAuxiliar);

        if (id == 0) { // Mostrar todos los contenidos
            contenidoAdapter.clear();
            contenidoAdapter.addAll(listaContenidosAuxiliar);
            contenidoAdapter.notifyDataSetChanged();
            return;
        }

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
        apiManager.getContenidos(token,new Callback<ArrayList<ContenidoDto>>() {
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

    public void obtenerListaUsuario(String token, int userId) {

        apiManager.getUserById(token, userId, new Callback<UserDto>() {
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

        updateUserDTO.setId(idUsuario);
        updateUserDTO.setContenidos(listaActualizada);

    }

    public void updateUsuario(String token, int id, UpdateUserDTO updateUserDTO) {
        System.out.println("ACTUALIZAR USER EXPLORAR: " + id + ": " + updateUserDTO.toString());
        apiManager.updateUser(token, id, updateUserDTO, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Fallo actualizando el usuario (Explorar)" + response.toString());
                } else {
                    Toast.makeText(Explorar.this, "El contenido se ha añadido correctamente. ", Toast.LENGTH_SHORT).show();
                    obtenerListaUsuario(token, idUsuario);
                    contenidoAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado

                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                System.err.println("FALLO EN updateUsuario (Explorar)" + t.getMessage().toString());
            }
        });
    }
}