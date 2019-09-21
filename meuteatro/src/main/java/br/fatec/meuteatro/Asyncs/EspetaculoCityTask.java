package br.fatec.meuteatro.Asyncs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.banco.EspetaculoDAO;
import br.fatec.meuteatro.banco.LocalEspetaculoDAO;
import br.fatec.meuteatro.beans.EspetaculoBean;

/**
 * Created by ismael on 05/11/15.
 */
public class EspetaculoCityTask extends AsyncTask<String, Integer, List<EspetaculoBean>>{

    Context context;

    public AsyncEspetaculoResponse response = null;

    public EspetaculoCityTask (Activity activity){
        context = activity.getBaseContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<EspetaculoBean> listEspetaculo) {
        response.listEspetaculoReturn(listEspetaculo);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<EspetaculoBean> doInBackground(String... params) {

        String city = params[0];
        EspetaculoDAO eDao = new EspetaculoDAO();
        LocalEspetaculoDAO localEspetaculoDao = new LocalEspetaculoDAO(context);
        List<EspetaculoBean> listEspetaculo = new ArrayList<EspetaculoBean>();

        listEspetaculo = eDao.buscaEspetaculoPorCidade(city);
        //******* GRAVA LOCAL **********
        localEspetaculoDao.open();
        for(EspetaculoBean esp : listEspetaculo){
            if(localEspetaculoDao.getItemExist(esp.getId_e()) == true){
                System.out.println("Atualizando espetaculo na base de dados local");
                localEspetaculoDao.alter(esp);
            } else {
                localEspetaculoDao.create(esp);
            }
        }
        localEspetaculoDao.close();

        return listEspetaculo;
    }
}
