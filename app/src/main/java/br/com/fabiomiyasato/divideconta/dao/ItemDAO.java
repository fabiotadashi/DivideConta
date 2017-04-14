package br.com.fabiomiyasato.divideconta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.fabiomiyasato.divideconta.model.Item;


/**
 * Created by FabioMiyasato on 16/03/17.
 */

public class ItemDAO {

    private SQLiteDatabase db;
    private DBOpenHelper banco;
    private Context context;

    public ItemDAO(Context context) {
        this.context = context;
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_ITEM = "item";

    private static final String COLUNA_ID = "id";
    private static final String COLUNA_DESCRICAO = "descricao";
    private static final String COLUNA_VALOR = "valor";


    public String add(Item item){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_DESCRICAO, item.getDescricao());
        values.put(COLUNA_VALOR, item.getValor());

        resultado = db.insert(TABELA_ITEM,
                null,
                values);

        db.close();

        if(resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }


    public String atualizar(Item item){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_DESCRICAO, item.getDescricao());
        values.put(COLUNA_VALOR, item.getValor());

        resultado = db.update(TABELA_ITEM,
                values,
                "id = "+item.getId(),
                null);

        db.close();

        if(resultado == -1) {
            return "Erro ao atualizar registro";
        } else {
            return "Registro atualizado com sucesso";
        }
    }


    public String delete(int id){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();


        resultado = db.delete(TABELA_ITEM,
                "id = "+id,
                null);

        db.close();

        if(resultado == -1) {
            return "Erro ao atualizar registro";
        } else {
            return "Registro atualizado com sucesso";
        }
    }


    public List<Item> getAll() {
        List<Item> items = new LinkedList<>();

        String rawQuery = "SELECT * FROM " + this.TABELA_ITEM;

        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery(rawQuery, null);

        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();

                item.setId(cursor.getInt(0));
                item.setDescricao(cursor.getString(1));
                item.setValor(cursor.getDouble(2));

                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public Item getBy(int id) {

        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_DESCRICAO, COLUNA_VALOR};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_ITEM, colunas, where, null, null, null, null, null);

        Item item = null;

        if(cursor != null)
        {
            cursor.moveToFirst();
            item = new Item();
            item.setDescricao(cursor.getString(cursor.getColumnIndex(COLUNA_DESCRICAO)));
            item.setValor(Double.valueOf(cursor.getString(cursor.getColumnIndex(COLUNA_VALOR))));
            item.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return item;
    }

}
