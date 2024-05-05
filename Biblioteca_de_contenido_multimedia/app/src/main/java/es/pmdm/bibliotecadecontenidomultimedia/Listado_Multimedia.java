    package es.pmdm.bibliotecadecontenidomultimedia;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import android.widget.SearchView;
    import android.widget.Toast;

    import com.google.android.material.floatingactionbutton.FloatingActionButton;

    import java.util.ArrayList;
    import java.util.List;

    import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
    import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class Listado_Multimedia extends AppCompatActivity{

        ListView listView;
        SearchView searchView;
        FloatingActionButton btnNueva;
        ApiManager apiManager;

        List<Contenido> listaGenerica;
        ArrayList<Contenido> contenidoUsuario = new ArrayList<>();

        UserDto user;

        ArrayAdapter<Contenido> arrayAdapter;


        ContenidoAdapter contenidoAdapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listado_multimedia);
            int idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

            if (idUsuario != -1) {
                searchView = findViewById(R.id.search_bar);
                listView = findViewById(R.id.listView);
                btnNueva = findViewById(R.id.btnNueva);
                apiManager = new ApiManager();

                obtenerListaUsuario(idUsuario);


                arrayAdapter = new ContenidoAdapter(this, R.layout.item_view, contenidoUsuario);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(Listado_Multimedia.this, Datos_Multimedia.class);
                        intent.putExtra("POSICION", position);
                    }
                });


                // SearchView
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Listado_Multimedia.this.arrayAdapter.getFilter().filter(query);

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                       Listado_Multimedia.this.arrayAdapter.getFilter().filter(newText);

                        return false;
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


        public void obtenerListaUsuario(int userId) {
            System.out.println(userId);
            apiManager.getUserById(userId, new Callback<UserDto>() {
                @Override
                public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(Listado_Multimedia.this, "Codigo (Listado_Multimedia) :  " + response.code(), Toast.LENGTH_SHORT).show();

                    }
                    user = response.body();
                    if (user != null) {
                        contenidoUsuario.addAll(user.getContenidos());

                        for (Contenido con :
                                contenidoUsuario) {
                            System.out.println(con.getTitulo() +", " + con.getAnyo() + " " + con.getPuntuacion() + " " + con.getAutor() );
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserDto> call, Throwable t) {
                    Toast.makeText(Listado_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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