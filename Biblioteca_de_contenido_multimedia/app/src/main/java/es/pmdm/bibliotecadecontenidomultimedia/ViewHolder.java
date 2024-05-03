package es.pmdm.bibliotecadecontenidomultimedia;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{

    TextView titulo;
    TextView autor;
    TextView ano;
    TextView categoria;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titulo = itemView.findViewById(R.id.titulo);
        autor  = itemView.findViewById(R.id.autor);
        ano    = itemView.findViewById(R.id.ano);
        categoria = itemView.findViewById(R.id.categoria);
    }
}
