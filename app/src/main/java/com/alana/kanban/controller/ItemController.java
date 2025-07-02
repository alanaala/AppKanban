package com.alana.kanban.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alana.kanban.model.DbHelper;
import com.alana.kanban.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemController {

    private final DbHelper dbHelper;

    public ItemController(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void inserir(Item item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.TITULO, item.getTitulo());
        values.put(DbHelper.DESCRICAO, item.getDescricao());
        values.put(DbHelper.STATUS, item.getStatus());
        values.put(DbHelper.IMAGEM_URI, item.getImagemUri());

        db.insert(DbHelper.TABLE, null, values);
        db.close();
    }

    public void atualizar(Item item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.TITULO, item.getTitulo());
        values.put(DbHelper.DESCRICAO, item.getDescricao());
        values.put(DbHelper.STATUS, item.getStatus());
        values.put(DbHelper.IMAGEM_URI, item.getImagemUri());

        db.update(DbHelper.TABLE, values, DbHelper.ID + " = ?", new String[]{String.valueOf(item)});
        db.close();
    }

    public void deletar(int item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DbHelper.TABLE, DbHelper.ID + " = ?", new String[]{String.valueOf(item)});
        db.close();
    }

    public List<Item> buscarPorStatus(String status) {
        List<Item> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DbHelper.TABLE, null, DbHelper.STATUS + " = ?", new String[]{status}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.ID));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.TITULO));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.DESCRICAO));
                String statusItem = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.STATUS));
                String imagemUri = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.IMAGEM_URI));

                lista.add(new Item(id, titulo, descricao, statusItem, imagemUri));
            }
            cursor.close();
        }
        db.close();
        return lista;
    }

    public Item buscarPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Item item = null;

        Cursor cursor = db.query(DbHelper.TABLE, null, DbHelper.ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.TITULO));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.DESCRICAO));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.STATUS));
                String imagemUri = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.IMAGEM_URI));

                item = new Item(id, titulo, descricao, status, imagemUri);
            }
            cursor.close();
        }
        db.close();
        return item;
    }
}