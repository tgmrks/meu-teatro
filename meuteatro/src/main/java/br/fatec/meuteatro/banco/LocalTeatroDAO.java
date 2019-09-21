package br.fatec.meuteatro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael on 28/10/15.
 */
public class LocalTeatroDAO {

//    "CREATE TABLE teatros (id_t INTEGER PRIMARY KEY, " +
//    "nome_teatro TEXT, cnpj TEXT, endereco TEXT, cidade TEXT, uf TEXT, tel TEXT)";

    public static final String TABELA = "teatros";
    public static final String COLUNA_ID = "id_t";
    public static final String COLUNA_NOME = "nome_teatro";
    public static final String COLUNA_CNPJ = "cnpj";
    public static final String COLUNA_END = "endereco";
    public static final String COLUNA_CIDADE = "cidade";
    public static final String COLUNA_UF = "uf";
    public static final String COLUNA_TEL = "tel";

    private SQLiteDatabase banco;
    private DadosLocaisHelper dbHelper;
    private String[] todasAsColunas = {COLUNA_ID, COLUNA_NOME, COLUNA_CNPJ, COLUNA_END, COLUNA_CIDADE, COLUNA_UF, COLUNA_TEL};

    public LocalTeatroDAO(Context context) {
        dbHelper = new DadosLocaisHelper(context);
    }

    public void open(){
        banco = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
//        if (dbHelper!=null)
//            dbHelper.close();
    }

                //create(int id_t, String nome, String cnpj, String end, String cidade, String uf, String tel)
    public void create(TeatroBean bean){
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_ID, bean.getId_t());
        valores.put(COLUNA_NOME, bean.getNome_teatro());
        valores.put(COLUNA_CNPJ, bean.getCnpj());
        valores.put(COLUNA_END, bean.getEndereco());
        valores.put(COLUNA_CIDADE, bean.getCidade());
        valores.put(COLUNA_UF, bean.getUf());
        valores.put(COLUNA_TEL, bean.getTel());
        banco.insert(TABELA, null, valores);
    }

    public void deletar (TeatroBean bean) {
        long id = bean.getId_t();
        banco.delete(TABELA, COLUNA_ID + "=" + id, null);
    }

    public void alter(TeatroBean bean){
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_ID, bean.getId_t());
        valores.put(COLUNA_NOME, bean.getNome_teatro());
        valores.put(COLUNA_CNPJ, bean.getCnpj());
        valores.put(COLUNA_END, bean.getEndereco());
        valores.put(COLUNA_CIDADE, bean.getCidade());
        valores.put(COLUNA_UF, bean.getUf());
        valores.put(COLUNA_TEL, bean.getTel());
        banco.update(TABELA,valores, COLUNA_ID + " = " + bean.getId_t(), null );
    }

    public List<TeatroBean> getAllItems () {
        List<TeatroBean> itens = new ArrayList<TeatroBean>();
        Cursor cursor = banco.query(TABELA, todasAsColunas, null, null, null, null, COLUNA_ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            TeatroBean bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return itens;
    }
//TENTAR FAZER GENERICO
//    public TeatroBean getItem (TeatroBean bean) {
//
//        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + bean.getId_t(), null, null, null, null);
//        cursor.moveToFirst();
//        return getCursor(cursor);
//    }
    public TeatroBean getItemId (int id) {
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        return getCursor(cursor);
    }

    public List<TeatroBean> getItemCity (String city) {
        List<TeatroBean> itens = new ArrayList<TeatroBean>();
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_CIDADE + " = " + "'" + city + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            TeatroBean bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return itens;
    }

    private TeatroBean getCursor (Cursor cursor) {
        TeatroBean bean = new TeatroBean();
//    "CREATE TABLE teatros (id_t INTEGER PRIMARY KEY, " +
//    "nome_teatro TEXT, cnpj TEXT, endereco TEXT, cidade TEXT, uf TEXT, tel TEXT)";
        bean.setId_t(cursor.getInt(0));
        bean.setNome_teatro(cursor.getString(1));
        bean.setCnpj(cursor.getString(2));
        bean.setEndereco(cursor.getString(3));
        bean.setCidade(cursor.getString(4));
        bean.setUf(cursor.getString(5));
        bean.setTel(cursor.getString(6));
        return bean;
    }

    public boolean getItemExist (int id_t) {
        //boolean result = false;
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + id_t, null, null, null, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
}
