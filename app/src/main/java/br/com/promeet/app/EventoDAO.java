package br.com.promeet.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

public class EventoDAO extends SQLiteOpenHelper {
    private static final String DATABASE = "BancoEventos";
    private static final int VERSAO = 1;

    public EventoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    public void onCreate(@NonNull SQLiteDatabase db) {
        String ddl ="CREATE TABLE Eventos (id INTEGER PRIMARY KEY,"
                + " evento TEXT, usuario TEXT, grupoID INTEGER, data TEXT, hora TEXT, local TEXT);";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int velha, int nova) {
        String ddl ="DROP TABLE IF EXISTS Eventos";
        db.execSQL(ddl);
        onCreate(db);
    }

    public void salvar(@NonNull EventoValue eventoValue) {
        ContentValues values = new ContentValues();
        values.put("evento",eventoValue.getEvento());
        values.put("usuario",eventoValue.getUsuario());
        values.put("grupoID",eventoValue.getGrupoID());
        values.put("data",eventoValue.getData());
        values.put("hora",eventoValue.getHora());
        values.put("local",eventoValue.getLocal());
        getWritableDatabase().insert("Eventos",null,values);
    }

    public List getLista(){
        List<EventoValue> eventos = new LinkedList<EventoValue>();
        String query = "SELECT * FROM " + "Eventos order by evento";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        EventoValue evento;
        if (cursor.moveToFirst()) {
            do {
                evento = new EventoValue();
                evento.setId(Long.parseLong(cursor.getString(0)));
                evento.setEvento(cursor.getString(1));
                evento.setUsuario(cursor.getString(2));
                evento.setGrupoID(Long.parseLong(cursor.getString(3)));
                evento.setData(cursor.getString(4));
                evento.setHora(cursor.getString(5));
                evento.setLocal(cursor.getString(6));
                eventos.add(evento);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    public void deletar(@NonNull EventoValue eventoValue) {
        String[] args = { eventoValue.getId().toString() };
        getWritableDatabase().delete("Eventos", "id=?", args);
    }

    public void dropAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String ddl ="DROP TABLE IF EXISTS Eventos";
        db.execSQL(ddl);
        onCreate(db);
    }

    public void alterar(@NonNull EventoValue eventoValue) {
        ContentValues values = new ContentValues();
        values.put("evento",eventoValue.getEvento());
        values.put("usuario",eventoValue.getUsuario());
        values.put("grupoID",eventoValue.getGrupoID());
        values.put("data",eventoValue.getData());
        values.put("hora",eventoValue.getHora());
        values.put("local",eventoValue.getLocal());
        getWritableDatabase().update("Disciplina", values,
                "id=?", new String[]{eventoValue.getId().toString()});
    }
}
