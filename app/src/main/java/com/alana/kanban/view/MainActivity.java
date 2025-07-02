package com.alana.kanban.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alana.kanban.R;
import com.alana.kanban.controller.ItemController;
import com.alana.kanban.model.Item;
import com.alana.kanban.model.TarefaAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerAFazer, recyclerAndamento, recyclerConcluido;
    TarefaAdapter adapterAFazer, adapterAndamento, adapterConcluido;
    ItemController itemController;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerAFazer = findViewById(R.id.recyclerAFazer);
        recyclerAndamento = findViewById(R.id.recyclerAndamento);
        recyclerConcluido = findViewById(R.id.recyclerConcluido);

        findViewById(R.id.btnNovaTarefa).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, NovaTarefaActivity.class));
        });
        itemController = new ItemController(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListas();
    }

    private void carregarListas() {

        List<Item> listaAFazer = itemController.buscarPorStatus("A FAZER");
        List<Item> listaAndamento = itemController.buscarPorStatus("EM ANDAMENTO");
        List<Item> listaConcluido = itemController.buscarPorStatus("CONCLUÍDO");

        adapterAFazer = new TarefaAdapter(listaAFazer, this);
        adapterAndamento = new TarefaAdapter(listaAndamento, this);
        adapterConcluido = new TarefaAdapter(listaConcluido, this);

        configurarRecyclerView(recyclerAFazer, adapterAFazer);
        configurarRecyclerView(recyclerAndamento, adapterAndamento);
        configurarRecyclerView(recyclerConcluido, adapterConcluido);
    }

    private void configurarRecyclerView(RecyclerView recyclerView, TarefaAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
