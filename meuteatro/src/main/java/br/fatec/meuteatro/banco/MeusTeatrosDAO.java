package br.fatec.meuteatro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.beans.MeusTeatrosBean;

/**
 * Created by ismael on 29/04/15.
 */
public class MeusTeatrosDAO {

//    "CREATE TABLE meus_teatros (_id INTEGER PRIMARY KEY, " +
//            "id_t INTEGER, nome_teatro TEXT, cidade TEXT, uf TEXT, endereco TEXT)";

    public static final String TABELA = "meus_teatros";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_TEATRO = "nome_teatro";
    public static final String COLUNA_CIDADE = "cidade";
    public static final String COLUNA_UF = "uf";
    public static final String COLUNA_ENDERECO = "endereco";
    public static final String COLUNA_ID_T = "id_t";
    public static final String COLUNA_CHECKED = "checked";

    private SQLiteDatabase banco;
    private DadosLocaisHelper dbHelper;
    private String[] todasAsColunas = {COLUNA_ID, COLUNA_TEATRO, COLUNA_CIDADE, COLUNA_UF, COLUNA_ENDERECO, COLUNA_ID_T, COLUNA_CHECKED};

    //CRIAÇÃO
    public MeusTeatrosDAO(Context contexto) {
        dbHelper = new DadosLocaisHelper(contexto);
    }

    public void open() {
        banco = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void create (String nome_teatro, String cidade, String uf, String endereco, int id_t) {

        ContentValues valores = new ContentValues();

        valores.put(COLUNA_TEATRO, nome_teatro);
        valores.put(COLUNA_CIDADE, cidade);
        valores.put(COLUNA_UF, uf);
        valores.put(COLUNA_ENDERECO, endereco);
        valores.put(COLUNA_ID_T, id_t);
        valores.put(COLUNA_CHECKED, 0);

        banco.insert(TABELA, null, valores);
    }

    public void deletar (MeusTeatrosBean bean) {
        long id = bean.get_id();
        banco.delete(TABELA, COLUNA_ID + "=" + id, null);
    }

    public void alter (MeusTeatrosBean bean) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_TEATRO, bean.getNome_teatro());
        valores.put(COLUNA_CIDADE, bean.getCidade());
        valores.put(COLUNA_UF, bean.getUf());
        valores.put(COLUNA_ENDERECO, bean.getEndereco());
        valores.put(COLUNA_ID_T, bean.getId_t());
        valores.put(COLUNA_CHECKED, bean.getChecked());
        banco.update(TABELA, valores, COLUNA_ID + " = " + bean.get_id(), null);
    }

    public void alterChecked (int id_t, int checked) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_CHECKED, checked);
        banco.update(TABELA, valores, COLUNA_ID_T + " = " + id_t, null);
    }

    public void alterAllChecked (int checked) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_CHECKED, checked);
        banco.update(TABELA, valores, null, null);
    }

    public List<MeusTeatrosBean> getAllItems () {

        List<MeusTeatrosBean> itens = new ArrayList<MeusTeatrosBean>();

        Cursor cursor = banco.query(TABELA, todasAsColunas, null, null, null, null, COLUNA_TEATRO);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            MeusTeatrosBean bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return itens;
    }

    public MeusTeatrosBean getItem (int id) {

        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID_T + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        return getCursor (cursor);
    }

    private MeusTeatrosBean getCursor (Cursor cursor) {

        MeusTeatrosBean bean = new MeusTeatrosBean();

        bean.set_id (cursor.getInt(0));
        bean.setId_t(cursor.getInt(5));
        bean.setNome_teatro (cursor.getString(1));
        bean.setCidade (cursor.getString(2));
        bean.setUf (cursor.getString(3));
        bean.setEndereco(cursor.getString(4));
        bean.setChecked(cursor.getInt(6));

        return bean;
    }

    public boolean getItemExist (int id_t) {

        //boolean result = false;

        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID_T + " = " + id_t, null, null, null, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

}
