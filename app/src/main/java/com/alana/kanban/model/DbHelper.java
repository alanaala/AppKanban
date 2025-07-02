package com.alana.kanban.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Kanban.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE = "Item";
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String STATUS = "status";
    public static final String IMAGEM_URI = "imagemUri";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITULO + " TEXT NOT NULL, " +
                DESCRICAO + " TEXT, " +
                STATUS + " TEXT NOT NULL, " +
                IMAGEM_URI + " TEXT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
