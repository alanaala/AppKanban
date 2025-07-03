package com.alana.kanban.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alana.kanban.R;
import com.alana.kanban.controller.ItemController;
import com.alana.kanban.model.Item;

import java.io.ByteArrayOutputStream;

public class NovaTarefaActivity extends AppCompatActivity {

    EditText edtTitulo, edtDescricao;
    Spinner spinnerStatus;
    ImageView imgPreview;
    Uri imagemUri;
    ItemController db;
    final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescricao = findViewById(R.id.edtDescricao);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        imgPreview = findViewById(R.id.imgPreview);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnSelecionarImagem = findViewById(R.id.btnSelecionarImagem);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        db = new ItemController(getApplicationContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        btnCamera.setOnClickListener(v -> abrirCamera());
        btnSalvar.setOnClickListener(v -> salvar());

        btnSelecionarImagem.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST);

        });

        btnVoltar.setOnClickListener(v -> {
            startActivity(new Intent(NovaTarefaActivity.this, MainActivity.class));
        });

    }

    private void abrirCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                imgPreview.setImageURI(imageUri);
            } else if (requestCode == CAMERA_REQUEST && data != null && data.getExtras() != null) {
                Bitmap foto = (Bitmap) data.getExtras().get("data");
                imgPreview.setImageBitmap(foto);
                Uri uri = getImageUri(foto);
            }
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "FotoTarefa", null);
        return Uri.parse(path);
    }

    private void salvar() {
        String titulo = edtTitulo.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();
        String uri = imagemUri != null ? imagemUri.toString() : "";

        if (titulo.isEmpty()) {
            Toast.makeText(this, "Digite um título", Toast.LENGTH_SHORT).show();
            return;
        }

        Item i = new Item();
        i.titulo = titulo;
        i.descricao = descricao;
        i.status = status;
        i.imagemUri = uri;

        db.inserir(i);
        Toast.makeText(this, "Tarefa salva!", Toast.LENGTH_SHORT).show();
        finish();
    }

}
