package br.fatec.meuteatro.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ismael on 29/04/15.
 */
public class DadosLocaisHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "local_info";
    private final static int    DB_VERSION = 1;

    //************ CRIAÇÃO DE TABELAS ****************
//    private static final String DADOS_CONFIG = "CREATE TABLE dados_config (_id INTEGER PRIMARY KEY, " +
//            "acesso BOOLEAN, cidade TEXT, utildados CHAR, atualizacao CHAR)";
    private static final String MINHAS_CIDADES = "CREATE TABLE minhas_cidades (_id INTEGER PRIMARY KEY, " +
            "cidade TEXT, uf TEXT, checked INTEGER)";

    private static final String MEUS_TEATROS = "CREATE TABLE meus_teatros (_id INTEGER PRIMARY KEY, " +
            "nome_teatro TEXT, cidade TEXT, uf TEXT, endereco TEXT, id_t INTEGER, checked INTEGER)";
    //nome_teatro TEXT > cidade TEXT > uf TEXT > endereco TEXT > id_t INTEGER)";

    private static final String TEATROS = "CREATE TABLE teatros (id_t INTEGER PRIMARY KEY, " +
            "nome_teatro TEXT, cnpj TEXT, endereco TEXT, cidade TEXT, uf TEXT, tel TEXT)";

    private static final String ESPETACULOS = "CREATE TABLE espetaculos (id_e INTEGER PRIMARY KEY, " +
            "id_t INTEGER, titulo TEXT, descricao TEXT, classificacao TEXT, data_hora TEXT, imagem BLOB, " +
            "entrada TEXT, link_externo TEXT)";

    private static final String AGENDAS = "CREATE TABLE agendas (id_a INTEGER PRIMARY KEY, " +
            "id_e INTEGER, dia INTEGER, mes INTEGER, ano INTEGER, hora TEXT)";

    //********** UPDATES DE TABELAS ******************

    private static final String DATABASE_ALTER_UPDATE_1 = "ALTER TABLE "
            + "meus_teatros ADD COLUMN checked INTEGER;";

    public DadosLocaisHelper (Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
    }

    @Override //solicita criação da tabela
    public void onCreate (SQLiteDatabase banco){
        banco.execSQL(MINHAS_CIDADES);
        banco.execSQL(MEUS_TEATROS);
        banco.execSQL(TEATROS);
        banco.execSQL(ESPETACULOS);
        banco.execSQL(AGENDAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //um SWITCH CASE não seria melhor? R.: depende do caso, para atualizações em cascata o if abaoixo esta ok

//        if (oldVersion < 2) {
//            db.execSQL(DATABASE_ALTER_UPDATE_1);
//
//        }
        //add how many iterations you need
    }

    //METODO BRUTO, perde to-do conteudo para fazer o update
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
//        onCreate(db);
//    }


}
