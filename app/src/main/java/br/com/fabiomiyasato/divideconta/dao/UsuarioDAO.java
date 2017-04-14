package br.com.fabiomiyasato.divideconta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.fabiomiyasato.divideconta.model.Usuario;

/**
 * Created by FabioMiyasato on 15/03/17.
 */

public class UsuarioDAO {

    private DBOpenHelper banco;

    public UsuarioDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public static final String TABELA_USUARIO = "usuarios";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_SENHA = "senha";

    public void add(Usuario usuario){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_SENHA, usuario.getSenha());

        resultado = db.insert(TABELA_USUARIO,
                null,
                values);

        db.close();

        if(resultado == -1) {
            // "Erro ao inserir registro";
        }
    }


    public Usuario getByNome(String nome) {

        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_NOME, COLUNA_SENHA};
        String where = "nome = '" + nome+"'";
        Cursor cursor = db.query(true, TABELA_USUARIO, colunas, where, null, null, null, null, null);

        Usuario usuario = null;

        if(cursor != null)
        {

        }
        try {
            while (cursor.moveToNext()) {
                usuario = new Usuario();
                usuario.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                usuario.setSenha(cursor.getString(cursor.getColumnIndex(COLUNA_SENHA)));
                usuario.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
            }
        } finally {
            cursor.close();
        }

        return usuario;
    }

}
