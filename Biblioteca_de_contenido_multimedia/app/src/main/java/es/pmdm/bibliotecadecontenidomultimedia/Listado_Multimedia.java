    package es.pmdm.bibliotecadecontenidomultimedia;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
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
    import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
    import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class Listado_Multimedia extends AppCompatActivity implements SearchView.OnQueryTextListener {

        RecyclerView rvLista;
        SearchView txtBuscar;
        FloatingActionButton btnNueva;
        ApiManager apiManager;

        List<Contenido> listaGenerica;

        UserDto user;


        ContenidoAdapter contenidoAdapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listado_multimedia);
            int idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);

            if (idUsuario != -1) {
                txtBuscar = findViewById(R.id.txtBuscar);
                rvLista = findViewById(R.id.rvLista);
                btnNueva = findViewById(R.id.btnNueva);
                apiManager = new ApiManager();

                txtBuscar.setOnQueryTextListener(this);

                // Inicializa el adaptador una sola vez
                contenidoAdapter = new ContenidoAdapter(getApplicationContext(), obtenerListaUsuario(idUsuario));
                rvLista.setLayoutManager(new LinearLayoutManager(this));
                rvLista.setAdapter(contenidoAdapter);

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
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            // Implementa la lógica de búsqueda aquí si lo necesitas
            return false;
        }

        public List<Contenido> obtenerListaUsuario(int userId) {
            List<Contenido> lista = new ArrayList<>();

            System.out.println(userId);
            apiManager.getUserById(userId, new Callback<UserDto>() {
                @Override
                public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(Listado_Multimedia.this, "Codigo vomitivo :  " + response.code(), Toast.LENGTH_SHORT).show();

                    }

                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    user = response.body();
                    if (user != null) {
                        lista.addAll(user.getContenidos());

                        for (Contenido con :
                                lista) {
                            System.out.println(con.getTitulo());
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserDto> call, Throwable t) {
                    Toast.makeText(Listado_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            return lista;
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