package com.alana.kanban.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alana.kanban.R;
import com.alana.kanban.controller.ItemController;
import com.alana.kanban.model.Item;

public class DetalhesActivity extends AppCompatActivity {

    private EditText edtTitulo, edtDescricao;
    private Spinner spinnerStatus;
    private ImageView imgPreview;
    private Button btnSalvar, btnExcluir;

    private ItemController itemController;
    private Item item;
    private int tarefaId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescricao = findViewById(R.id.edtDescricao);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        imgPreview = findViewById(R.id.imgPreview);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);

        itemController = new ItemController(getApplicationContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);


        tarefaId = getIntent().getIntExtra("tarefa_id", -1);
        if (tarefaId == -1) {
            Toast.makeText(this, "Tarefa inválida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        item = itemController.buscarPorId(tarefaId);
        if (item == null) {
            Toast.makeText(this, "Tarefa não encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        carregarDados();

        btnSalvar.setOnClickListener(v -> salvar());
        btnExcluir.setOnClickListener(v -> confirmarExcluir());
    }

    private void carregarDados() {
        edtTitulo.setText(item.getTitulo());
        edtDescricao.setText(item.getDescricao());

        String[] statusArray = getResources().getStringArray(R.array.status_array);
        for (int i = 0; i < statusArray.length; i++) {
            if (statusArray[i].equals(item.getStatus())) {
                spinnerStatus.setSelection(i);
                break;
            }
        }
    }

    private void salvar() {
        String titulo = edtTitulo.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        if (titulo.isEmpty()) {
            Toast.makeText(this, "Digite um título", Toast.LENGTH_SHORT).show();
            return;
        }

        item.setTitulo(titulo);
        item.setDescricao(descricao);
        item.setStatus(status);

        itemController.atualizar(item);

        Toast.makeText(this, "Tarefa atualizada!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void confirmarExcluir() {
        new AlertDialog.Builder(this)
                .setTitle("Excluir tarefa")
                .setMessage("Tem certeza que deseja excluir esta tarefa?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    itemController.deletar(item.getId());
                    Toast.makeText(this, "Tarefa excluída", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
