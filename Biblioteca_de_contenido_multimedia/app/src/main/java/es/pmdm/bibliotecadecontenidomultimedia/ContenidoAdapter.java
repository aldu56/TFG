package es.pmdm.bibliotecadecontenidomultimedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.pmdm.bibliotecadecontenidomultimedia.Model.Contenido;

public class ContenidoAdapter extends RecyclerView.Adapter<ContenidoAdapter.ContenidoViewHolder> {
    private int mResource;

     ArrayList<Contenido> listaContenidoUsuario;

    private ArrayList<Contenido> listaFiltrar;


    public ContenidoAdapter(ArrayList<Contenido> listaContenidoUsuario) {
        this.listaContenidoUsuario = listaContenidoUsuario;
        listaFiltrar = new ArrayList<>();
        listaFiltrar.addAll(listaContenidoUsuario);
    }

    @NonNull
    @Override
    public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multimedia, null, false);
        return new ContenidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
        holder.txtTitulo.setText(listaContenidoUsuario.get(position).getTitulo());
        holder.txtAutor.setText(listaContenidoUsuario.get(position).getAutor());
        holder.txtAno.setText(listaContenidoUsuario.get(position).getAnyo());
        holder.txtCategoria.setText(listaContenidoUsuario.get(position).getCategoria());


    }

    @Override
    public int getItemCount() {
        return listaContenidoUsuario.size();
    }


    public class ContenidoViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtAutor, txtAno, txtCategoria;

        public ContenidoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.titulo);
            txtAutor = itemView.findViewById(R.id.autor);
            txtAno = itemView.findViewById(R.id.ano);
            txtCategoria = itemView.findViewById(R.id.categoria);
        }
    }
}
