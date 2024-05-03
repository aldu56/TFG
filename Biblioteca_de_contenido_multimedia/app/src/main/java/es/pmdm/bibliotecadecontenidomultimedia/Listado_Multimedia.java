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
import es.pmdm.bibliotecadecontenidomultimedia.Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  Listado_Multimedia extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView rvLista;
    ArrayList<Contenido> listaContenidoUsuario;
    SearchView txtBuscar;
    FloatingActionButton btnNueva;
    ApiManager apiManager;

    List<Contenido> listaGenerica;

    User user ;
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

        System.out.println(idUsuario);

        obtenerListaUsuario(idUsuario);


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

        apiManager.getUserById(userId, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Listado_Multimedia.this, "Codigo:  " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Listado_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        List<Contenido> listaUserList = user.getContenidos();

        for (Contenido con :
                listaUserList) {
            listaUsuario.add(con);
        }



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
                Toast.makeText(Listado_Multimedia.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}