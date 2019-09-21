package br.fatec.meuteatro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.beans.MeusTeatrosBean;

/**
 * Created by ismael on 09/11/15.
 */
public class MinhasCidadesDAO {

    public static final String TABELA = "minhas_cidades";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_CIDADE = "cidade";
    public static final String COLUNA_UF = "uf";
    public static final String COLUNA_CHECKED = "checked";

//    "CREATE TABLE minhas_cidades (_id INTEGER PRIMARY KEY, " +
//            "cidade TEXT, uf TEXT, checked INTEGER)";

    private SQLiteDatabase banco;
    private DadosLocaisHelper dbHelper;
    private String[] todasAsColunas = {COLUNA_ID, COLUNA_CIDADE, COLUNA_UF, COLUNA_CHECKED};

    //CRIAÇÃO
    public MinhasCidadesDAO(Context contexto) {
        dbHelper = new DadosLocaisHelper(contexto);
    }

    public void open() {
        banco = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void create (String cidade, String uf) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_CIDADE, cidade);
        valores.put(COLUNA_UF, uf);
        valores.put(COLUNA_CHECKED, 0);
        banco.insert(TABELA, null, valores);
    }

//    public void deletar (String cidade) {
//        banco.delete(TABELA, COLUNA_CIDADE + "=" + "'" + cidade + "'", null);
//    }

    public void alter (String cidade, String uf, int checked) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_CIDADE, cidade);
        valores.put(COLUNA_UF, uf);
        valores.put(COLUNA_CHECKED, checked);
        banco.update(TABELA, valores, COLUNA_CIDADE + " = " + "'" + cidade + "'", null);
    }

    public void alterChecked (String cidade, int checked) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_CHECKED, checked);
        banco.update(TABELA, valores, COLUNA_CIDADE + " = " + "'" + cidade + "'", null);
    }

    public void alterAllChecked (int checked) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_CHECKED, checked);
        banco.update(TABELA, valores, null, null);
    }

    public List<String[]> getAllItems () {

        List<String[]> itens = new ArrayList<String[]>();

        Cursor cursor = banco.query(TABELA, todasAsColunas, null, null, null, null, COLUNA_CIDADE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            String[] bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return itens;
    }

    public String[] getItem(String cidade) {

        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_CIDADE + " = " + "'" + cidade + "'", null, null, null, null);
        cursor.moveToFirst();
        return getCursor (cursor);
    }

    private String[] getCursor (Cursor cursor) {

        String[] bean = {"","","",""};

        bean[0] = String.valueOf(cursor.getInt(3));//id
        bean[1] = cursor.getString(1);//cidade
        bean[2] = cursor.getString(2);//uf
        bean[3] = String.valueOf(cursor.getInt(3));//checked

        return bean;
    }

    public boolean getItemExist (String cidade) {

        //boolean result = false;

        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_CIDADE + " = " + "'" + cidade + "'", null, null, null, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
}
