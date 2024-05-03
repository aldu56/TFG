package es.pmdm.bibliotecadecontenidomultimedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;

public class ContenidoAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Contenido> contenidos;

    public ContenidoAdapter(Context context, List<Contenido> contenidos) {
        this.context = context;
        this.contenidos = contenidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titulo.setText(contenidos.get(position).getTitulo());
        holder.autor.setText(contenidos.get(position).getAutor());
        holder.ano.setText(contenidos.get(position).getAnyo());
        holder.categoria.setText(contenidos.get(position).getCategoria());
    }

    @Override
    public int getItemCount() {
        return contenidos.size();
    }
}
