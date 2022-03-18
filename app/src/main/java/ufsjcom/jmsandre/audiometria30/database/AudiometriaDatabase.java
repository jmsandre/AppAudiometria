package ufsjcom.jmsandre.audiometria30.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ufsjcom.jmsandre.audiometria30.model.Usuario;

public class AudiometriaDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "audiometria.db";
    private static final int VERSION = 1;

    final int COLUNA_CPF = 0;
    final int COLUNA_NOME = 1;
    final int COLUNA_VALORES_ESQUERDA = 2;
    final int COLUNA_VALORES_DIREITA = 3;

    public AudiometriaDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario(" +
                "nome TEXT," +
                "cpf TEXT PRIMARY KEY," +
                "valoresEsquerda TEXT," +
                "valoresDireita TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuario");
        onCreate(db);
    }

    public void inserir(Usuario usuario){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("cpf", usuario.getCpf());
        values.put("valoresEsquerda", usuario.getStringValoresEsquerda());
        values.put("valoresDireita", usuario.getStringValoresDireita());

        db.insert("usuario", null, values);
    }

    public void deletar(Usuario usuario){
        SQLiteDatabase db = getReadableDatabase();

        db.delete("usuario", "cpf = "+usuario.getCpf(), null);
    }

    public void clear(){
        SQLiteDatabase db = getReadableDatabase();

        db.execSQL("delete from usuario");
    }

    public List<Usuario> todos(){

        SQLiteDatabase db = getReadableDatabase();
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM usuario", null);

        if(cursor == null){
            Log.e("Database", "todos: Falha ao obter dados", null);
            return null;
        }

        cursor.moveToFirst();

        do{
            Usuario temp = new Usuario();

            temp.setNome(cursor.getString(COLUNA_NOME));
            temp.setCpf(cursor.getString(COLUNA_CPF));
            temp.setValoresEsquerda(cursor.getString(COLUNA_VALORES_ESQUERDA));
            temp.setValoresDireita(cursor.getString(COLUNA_VALORES_DIREITA));

            usuarios.add(temp);
        }while(cursor.moveToNext());

        return usuarios;
    }

    public List<Usuario> pesquisa(String string) {

        SQLiteDatabase db = getReadableDatabase();
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("SELECT *" +
                    "FROM usuario " +
                    "WHERE nome LIKE '%"+string+"%'", null);

        if(cursor == null){
            Log.e("Database", "todos: Falha ao obter dados", null);
            return null;
        }

        cursor.moveToFirst();

        do{
            Usuario temp = new Usuario();

            temp.setNome(cursor.getString(COLUNA_NOME));
            temp.setCpf(cursor.getString(COLUNA_CPF));
            temp.setValoresEsquerda(cursor.getString(COLUNA_VALORES_ESQUERDA));
            temp.setValoresDireita(cursor.getString(COLUNA_VALORES_DIREITA));

            usuarios.add(temp);
        }while(cursor.moveToNext());

        return usuarios;
    }
}
