package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;

public class Listado_Multimedia extends AppCompatActivity implements SearchView.OnQueryTextListener{

RecyclerView rvLista;
ArrayList<Contenido> listaContenidoUsuario;
SearchView txtBuscar;
FloatingActionButton btnNueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multimedia);

        txtBuscar = findViewById(R.id.txtBuscar);
        rvLista = findViewById(R.id.rvLista);
       btnNueva = findViewById(R.id.btnNueva);


      // ContenidoAdapter adapter = new ContenidoAdapter(); //TODO Añadir source de la lista



       txtBuscar.setOnQueryTextListener(this); // Para que se busque en tiempo real.
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}