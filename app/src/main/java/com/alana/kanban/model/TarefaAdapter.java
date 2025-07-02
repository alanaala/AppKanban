package com.alana.kanban.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.alana.kanban.R;
import com.alana.kanban.view.DetalhesActivity;
import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {
    private final List<Item> lista;
    private final Context context;

    public TarefaAdapter(List<Item> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tarefa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = lista.get(position);

        holder.txtTitulo.setText(item.getTitulo());
        holder.txtDescricao.setText(item.getDescricao());

        if (item.getImagemUri() != null && !item.getImagemUri().isEmpty()) {
            holder.imgPreview.setImageURI(Uri.parse(item.getImagemUri()));
        } else {
            holder.imgPreview.setImageResource(R.drawable.ic_baseline_note_24);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalhesActivity.class);
            intent.putExtra("tarefa_id", item.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescricao;
        ImageView imgPreview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
            imgPreview = itemView.findViewById(R.id.imgPreview);
        }
    }
}
