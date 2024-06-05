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

    ArrayList<ContenidoDto> contenidosAuxiliar = new ArrayList<>();

    UserDto user;
    int idUsuario;

    ArrayList<ContenidoDto> contenidoUsuarios = new ArrayList<>();
    ContenidoAdapter contenidoAdapter;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multimedia);

        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);
        token = getIntent().getStringExtra("TOKEN");

        getSupportActionBar().setTitle("Lista personal");


        if (idUsuario != -1) {
            listView = findViewById(R.id.listView);
            btnNueva = findViewById(R.id.btnNueva);
            apiManager = new ApiManager();

            obtenerListaUsuario(token, idUsuario);


            contenidoAdapter = new ContenidoAdapter(this, R.layout.item_view, contenidoUsuarios);

            listView.setAdapter(contenidoAdapter);
            registerForContextMenu(listView);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                ContenidoDto contenidoSeleccionado = contenidoAdapter.getItem(position);
                if (contenidoSeleccionado != null) {
                    String titulo = contenidoSeleccionado.getTitulo();
                    Intent intent = new Intent(Listado_Multimedia.this, Datos_Multimedia.class);
                    intent.putExtra("TITULO", titulo);
                    intent.putExtra("ID_USER", idUsuario);
                    intent.putExtra("TOKEN", token);
                    startActivity(intent);
                }
            });

            btnNueva.setOnClickListener(v -> {
                Intent intent = new Intent(Listado_Multimedia.this, Explorar.class);
                intent.putExtra("ID_USUARIO", idUsuario);
                intent.putExtra("TOKEN", token);
                startActivityForResult(intent, -15);
            });
        } else {
            Toast.makeText(this, "El id del usuario no se recibió correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == -15 && resultCode == RESULT_OK) {
            obtenerListaUsuario(token, idUsuario);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerListaUsuario(token, idUsuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        String sugerenciasBusqueda = getResources().getString(R.string.buscadorHint);
        searchView.setQueryHint(sugerenciasBusqueda);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    contenidoAdapter.clear();
                    contenidoAdapter.addAll(contenidosAuxiliar);
                    contenidoAdapter.notifyDataSetChanged();
                } else {
                    contenidoAdapter.filterByTitle(newText);
                }
                return true;
            }
        });

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (contenidoAdapter.getCount() == 0) {
                    searchView.setQuery("", false);
                    contenidoAdapter.clear();
                    contenidoAdapter.addAll(contenidosAuxiliar);
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
            case R.id.mas:
                startActivity(new Intent(Listado_Multimedia.this, More.class));
                return true;
            case R.id.about:
                startActivity(new Intent(Listado_Multimedia.this, About.class));
                return true;
            case R.id.filtroPeli:
                ordenarPorCategoria(1);
                return true;
            case R.id.filtroSerie:
                ordenarPorCategoria(2);
                return true;
            case R.id.filtroLibro:
                ordenarPorCategoria(3);
                return true;
            case R.id.todos:
                ordenarPorCategoria(0);
                return true;
            case R.id.borrar_todos:
                borrarTodosLosContenidos(token);
                return true;
            case R.id.txtPerfil:
                Intent intent = new Intent(Listado_Multimedia.this, user_info.class);
                intent.putExtra("ID_USUARIO", idUsuario);
                intent.putExtra("TOKEN", token);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ordenarPorCategoria(int id) {
        contenidoUsuarios.clear();
        contenidoUsuarios.addAll(contenidosAuxiliar);

        if (id == 0) { // Mostrar todos los contenidos
            contenidoAdapter.clear();
            contenidoAdapter.addAll(contenidosAuxiliar);
            contenidoAdapter.notifyDataSetChanged();
            return;
        }

        ArrayList<ContenidoDto> listaFiltrada = new ArrayList<>();

        for (ContenidoDto co : contenidoUsuarios) {
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
            }
        }

        if (!listaFiltrada.isEmpty()) {
            contenidoAdapter.clear();
            contenidoAdapter.addAll(listaFiltrada);
            contenidoAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No se encontraron contenidos para esta categoría.", Toast.LENGTH_SHORT).show();
        }
    }

    public void obtenerListaUsuario(String token, int userId) {
        apiManager.getUserById(token, userId, new Callback<UserDto>() {
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
                    contenidosAuxiliar.clear();
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (item.getItemId() == R.id.eliminar) {
            ContenidoDto contenidoEliminar = contenidoAdapter.getItem(position);

            eliminarContenidoDelUsuario(contenidoEliminar);

            contenidoUsuarios.remove(contenidoEliminar);
            contenidosAuxiliar.remove(contenidoEliminar);
            contenidoAdapter.notifyDataSetChanged();

            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void eliminarContenidoDelUsuario(ContenidoDto contenido) {
        ArrayList<ContenidoDto> listaActualizada = new ArrayList<>(contenidoUsuarios);
        listaActualizada.remove(contenido);

        user.setContenidos(listaActualizada);
        updateUsuario(token, idUsuario, user);
    }

    public void updateUsuario(String token, int id, UserDto userDto) {
        apiManager.updateUsers(token, id, userDto, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Listado_Multimedia.this, "Fallo actualizando el usuario", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Listado_Multimedia.this, "Lista actualizada correctamente", Toast.LENGTH_SHORT).show();
                    obtenerListaUsuario(token, idUsuario);
                    contenidoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(Listado_Multimedia.this, "FALLO EN updateUsuario (Listado_Multimedia): " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void borrarTodosLosContenidos(String token) {
        user.setContenidos(new ArrayList<ContenidoDto>());

        apiManager.updateUsers(token, idUsuario, user, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Listado_Multimedia.this, "Error al borrar contenidos: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(Listado_Multimedia.this, "Todos los contenidos han sido borrados.", Toast.LENGTH_SHORT).show();
                contenidoUsuarios.clear();
                contenidoAdapter.clear();
                contenidoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(Listado_Multimedia.this, "Error al borrar contenidos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}