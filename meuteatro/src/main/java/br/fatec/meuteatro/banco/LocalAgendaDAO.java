package br.fatec.meuteatro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.beans.AgendaBean;

/**
 * Created by ismael on 28/10/15.
 */
public class LocalAgendaDAO {

    //"CREATE TABLE agendas (id_a INTEGER PRIMARY KEY, " +
    //"id_e INTEGER, dia INTEGER, mes INTEGER, ano INTEGER, hora TEXT)";

    public static final String TABELA = "agendas";
    public static final String COLUNA_ID = "id_a";
    public static final String COLUNA_ID_E = "id_e";
    public static final String COLUNA_DIA = "dia";
    public static final String COLUNA_MES = "mes";
    public static final String COLUNA_ANO = "ano";
    public static final String COLUNA_HORA = "hora";

    private SQLiteDatabase banco;
    private DadosLocaisHelper dbHelper;
    private String[] todasAsColunas = {COLUNA_ID, COLUNA_ID_E, COLUNA_DIA, COLUNA_MES, COLUNA_ANO, COLUNA_HORA};

    public LocalAgendaDAO(Context context) {
        dbHelper = new DadosLocaisHelper(context);
    }

    public void open(){
        banco = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void create(AgendaBean bean){
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_ID, bean.getId_a());
        valores.put(COLUNA_ID_E, bean.getId_e());
        valores.put(COLUNA_DIA, bean.getDia());
        valores.put(COLUNA_MES, bean.getMes());
        valores.put(COLUNA_ANO, bean.getAno());
        valores.put(COLUNA_HORA, bean.getHora());
        banco.insert(TABELA, null, valores);
    }

    public void deletar (AgendaBean bean) {
        long id = bean.getId_a();
        banco.delete(TABELA, COLUNA_ID + "=" + id, null);
    }

    public void alter(AgendaBean bean){
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_ID, bean.getId_a());
        valores.put(COLUNA_ID_E, bean.getId_e());
        valores.put(COLUNA_DIA, bean.getDia());
        valores.put(COLUNA_MES, bean.getMes());
        valores.put(COLUNA_ANO, bean.getAno());
        valores.put(COLUNA_HORA, bean.getHora());
        banco.update(TABELA,valores, COLUNA_ID + " = " + bean.getId_a(), null );
    }

    public List<AgendaBean> getAllItems () {
        List<AgendaBean> itens = new ArrayList<AgendaBean>();
        Cursor cursor = banco.query(TABELA, todasAsColunas, null, null, null, null, COLUNA_ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            AgendaBean bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return itens;
    }

//    public AgendaBean getItem (AgendaBean bean) {
//        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + bean.getId_a(), null, null, null, null);
//        cursor.moveToFirst();
//        return getCursor(cursor);
//    }
    public AgendaBean getItemId (int id) {
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        return getCursor(cursor);
    }

    public List<AgendaBean> getItemEspetaculo (int id) {
        List<AgendaBean> itens = new ArrayList<AgendaBean>();
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID_E + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            AgendaBean bean = new AgendaBean();
            bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        return itens;
    }

    private AgendaBean getCursor (Cursor cursor) {
        AgendaBean bean = new AgendaBean();
        //"CREATE TABLE agendas (id_a INTEGER PRIMARY KEY, " +
        //"id_e INTEGER, dia INTEGER, mes INTEGER, ano INTEGER, hora TEXT)";
        bean.setId_a(cursor.getInt(0));
        bean.setId_e(cursor.getInt(1));
        bean.setDia(cursor.getInt(2));
        bean.setMes (cursor.getInt(3));
        bean.setAno(cursor.getInt(4));
        bean.setHora(cursor.getString(5));
        return bean;
    }

    public boolean getItemExist (int id_a) {
        //boolean result = false;
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + id_a, null, null, null, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
}
