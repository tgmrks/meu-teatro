package br.fatec.meuteatro.Asyncs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.banco.EspetaculoDAO;
import br.fatec.meuteatro.banco.LocalAgendaDAO;
import br.fatec.meuteatro.beans.AgendaBean;

/**
 * Created by ismael on 05/11/15.
 */
public class AgendaTask extends AsyncTask<Integer, Integer, List<AgendaBean>> {

    Context context;
    public AsyncAgendaResponse response = null;

    public AgendaTask (Activity activity){
        context = activity.getBaseContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<AgendaBean> agendaBeans) {

        //VERIFICAR SE IRÁ SALVAR OU NÃO

        response.listAgendaReturn(agendaBeans);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<AgendaBean> doInBackground(Integer... params) {

        int id = params[0];

        List<AgendaBean> listAgenda = new ArrayList<AgendaBean>();
        EspetaculoDAO eDao = new EspetaculoDAO();
        LocalAgendaDAO localAgendaDao = new LocalAgendaDAO(context);

        //******* BUSCA WS **********
        listAgenda = eDao.buscaAgenda(id);

        //******* GRAVA LOCAL **********
        localAgendaDao.open();
        for(AgendaBean age : listAgenda){
            if(localAgendaDao.getItemExist(age.getId_a())== true){
                System.out.println("Atualizando agenda na base de dados local");
                localAgendaDao.alter(age);
            } else {
                localAgendaDao.create(age);
            }
        }
        localAgendaDao.close();

        return null;
    }
}
