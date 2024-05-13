package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Explorar extends AppCompatActivity {


    ListView listView;
    private ArrayList<ContenidoDto> listaGenerica;
    private ArrayList<ContenidoDto> listaContenidos;
    private ArrayList<ContenidoDto> listaContenidosAuxiliar = new ArrayList<ContenidoDto>();
    ContenidoAdapter contenidoAdapter;

    ApiManager apiManager;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar);
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);


        listView = (ListView) findViewById(R.id.listViewEx);

        apiManager =  new ApiManager();
        contenidoAdapter = new ContenidoAdapter(this, R.layout.item_view, listaContenidos);
        listView.setAdapter(contenidoAdapter);

        contenidoAdapter.notifyDataSetChanged();


        registerForContextMenu(listView);



        //TODO Cuando clico en una pelicula de la lista, compruebo que no la tenga ya el user y la añado.
        // (Llamada api para ver lista user, y Llamada api para updatear user) Tambien Llamada api sacar todos los contenidos.


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
}