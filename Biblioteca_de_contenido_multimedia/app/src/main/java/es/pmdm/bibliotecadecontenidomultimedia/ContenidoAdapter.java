package es.pmdm.bibliotecadecontenidomultimedia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;
import es.pmdm.bibliotecadecontenidomultimedia.dto.ContenidoDto;

public class ContenidoAdapter extends ArrayAdapter<ContenidoDto> {
    private int mResource;
    private ArrayList<ContenidoDto> misContenidos;

    public ContenidoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ContenidoDto> objects) {
        super(context, resource, objects);
        mResource = resource;
        misContenidos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater miInflador = LayoutInflater.from(this.getContext());
        View miFila = miInflador.inflate(mResource, parent, false);

        TextView txtTitulo = miFila.findViewById(R.id.txtTitulo);
        txtTitulo.setText(misContenidos.get(position).getTitulo());

        TextView txtAutor = miFila.findViewById(R.id.txtAutor);
        txtAutor.setText(misContenidos.get(position).getAutor());

        TextView txtAnyo = miFila.findViewById(R.id.txtAno);
        txtAnyo.setText(String.valueOf(misContenidos.get(position).getAnyo()));

        TextView txtcategoria = miFila.findViewById(R.id.txtGenero);
        txtcategoria.setText(misContenidos.get(position).getGenero());

        return miFila;
    }
}
