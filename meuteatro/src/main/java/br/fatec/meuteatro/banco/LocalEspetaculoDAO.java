package br.fatec.meuteatro.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.beans.EspetaculoBean;

/**
 * Created by ismael on 28/10/15.
 */
public class LocalEspetaculoDAO {

//    "CREATE TABLE espetaculos (id_e INTEGER PRIMARY KEY, " +
//    "id_t INTEGER, titulo TEXT, descricao TEXT, classificacao TEXT, data_hora TEXT, imagem BLOB, " +
//    "entrada TEXT, link_externo TEXT)";

    public static final String TABELA = "espetaculos";
    public static final String COLUNA_ID = "id_e";
    public static final String COLUNA_ID_T = "id_t";
    public static final String COLUNA_TITULO = "titulo";
    public static final String COLUNA_DESC = "descricao";
    public static final String COLUNA_CLAS = "classificacao";
    public static final String COLUNA_DT_HR = "data_hora";
    public static final String COLUNA_IMG = "imagem";
    public static final String COLUNA_ENTRADA = "entrada";
    public static final String COLUNA_LINK = "link_externo";

    private SQLiteDatabase banco;
    private DadosLocaisHelper dbHelper;
    private String[] todasAsColunas = {COLUNA_ID, COLUNA_ID_T, COLUNA_TITULO, COLUNA_DESC, COLUNA_CLAS,
            COLUNA_DT_HR, COLUNA_IMG, COLUNA_ENTRADA, COLUNA_LINK};

    public LocalEspetaculoDAO(Context context) {
        dbHelper = new DadosLocaisHelper(context);
    }

    public void open(){
        banco = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

//    public void create(int id_e, int id_t, String titulo, String descricao, String classificacao,
//                       String data_hora, byte[] img , String entrada, String link){
    public void create(EspetaculoBean bean){

        ContentValues valores = new ContentValues();
        valores.put(COLUNA_ID, bean.getId_e());
        valores.put(COLUNA_ID_T, bean.getId_t());
        valores.put(COLUNA_TITULO, bean.getTitulo());
        valores.put(COLUNA_DESC, bean.getDescricao());
        valores.put(COLUNA_CLAS, bean.getClassificacao());
        valores.put(COLUNA_DT_HR, bean.getData_hora());
        valores.put(COLUNA_IMG, bean.getImagem());
        valores.put(COLUNA_ENTRADA, bean.getEntrada());
        valores.put(COLUNA_LINK, bean.getLink_externo());
        banco.insert(TABELA, null, valores);
    }

    public void deletar (EspetaculoBean bean) {
        long id = bean.getId_e();
        banco.delete(TABELA, COLUNA_ID + "=" + id, null);
    }

    public void alter(EspetaculoBean bean){

        ContentValues valores = new ContentValues();
        valores.put(COLUNA_ID, bean.getId_e());
        valores.put(COLUNA_ID_T, bean.getId_t());
        valores.put(COLUNA_TITULO, bean.getTitulo());
        valores.put(COLUNA_DESC, bean.getDescricao());
        valores.put(COLUNA_CLAS, bean.getClassificacao());
        valores.put(COLUNA_DT_HR, bean.getData_hora());
        valores.put(COLUNA_IMG, bean.getImagem());
        valores.put(COLUNA_ENTRADA, bean.getEntrada());
        valores.put(COLUNA_LINK, bean.getLink_externo());
        banco.update(TABELA,valores, COLUNA_ID + " = " + bean.getId_e(), null );
    }

    public List<EspetaculoBean> getAllItems () {

        List<EspetaculoBean> itens = new ArrayList<EspetaculoBean>();

        Cursor cursor = banco.query(TABELA, todasAsColunas, null, null, null, null, COLUNA_ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            EspetaculoBean bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return itens;
    }

//    public EspetaculoBean getItem (EspetaculoBean bean) {
//        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + bean.getId_t(), null, null, null, null);
//        cursor.moveToFirst();
//        return getCursor(cursor);
//    }
    public EspetaculoBean getItemId (int id) {
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        return getCursor(cursor);
    }
    public List<EspetaculoBean> getItemTeatro (int id) {
        List<EspetaculoBean> itens = new ArrayList<EspetaculoBean>();
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID_T + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            EspetaculoBean bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        return itens;
    }
    public List<EspetaculoBean> getItemCity (String city) {
        List<EspetaculoBean> itens = new ArrayList<EspetaculoBean>();
        //Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_CIDADE + " = " + "'" + city + "'", null, null, null, null);
        final String query = "SELECT * FROM espetaculos E INNER JOIN teatros T ON E.id_t = T.id_t AND T.cidade = ? order by E.id_t";
        Cursor cursor = banco.rawQuery(query, new String[]{city});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            EspetaculoBean bean = getCursor(cursor);
            itens.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return itens;
    }

    private EspetaculoBean getCursor (Cursor cursor) {
        EspetaculoBean bean = new EspetaculoBean();
//    "CREATE TABLE espetaculos (id_e INTEGER PRIMARY KEY, " +
//    "id_t INTEGER, titulo TEXT, descricao TEXT, classificacao TEXT, data_hora TEXT, imagem BLOB, " +
//    "entrada TEXT, link_externo TEXT)";
        bean.setId_e(cursor.getInt(0));
        bean.setId_t(cursor.getInt(1));
        bean.setTitulo(cursor.getString(2));
        bean.setDescricao(cursor.getString(3));
        bean.setClassificacao(cursor.getString(4));
        bean.setData_hora(cursor.getString(5));
        bean.setImagem(cursor.getBlob(6));
        bean.setEntrada(cursor.getString(7));
        bean.setLink_externo(cursor.getString(8));
        return bean;
    }

    public boolean getItemExist (int id_e) {
        //boolean result = false;
        Cursor cursor = banco.query(TABELA, todasAsColunas, COLUNA_ID + " = " + id_e, null, null, null, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
}
