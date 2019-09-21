package br.fatec.meuteatro.Asyncs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.fatec.meuteatro.banco.EspetaculoDAO;
import br.fatec.meuteatro.banco.LocalEspetaculoDAO;
import br.fatec.meuteatro.beans.EspetaculoBean;

/**
 * Created by ismael on 05/11/15.
 */
public class EspetaculoTeatroTask extends AsyncTask<Integer, Integer, List<EspetaculoBean>> {

    Context context;

    public AsyncEspetaculoResponse response;

    public EspetaculoTeatroTask (Activity activity){
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
    protected List<EspetaculoBean> doInBackground(Integer... params) {
        int id = params[0];
        EspetaculoDAO eDao = new EspetaculoDAO();
        LocalEspetaculoDAO localEspetaculoDao = new LocalEspetaculoDAO(context);

        List<EspetaculoBean> listEspetaculo;
        listEspetaculo = eDao.buscaEspetaculoPorTeatro(id);
        //******* GRAVA LOCAL **********
        if (listEspetaculo != null) {
            localEspetaculoDao.open();
            for (EspetaculoBean esp : listEspetaculo) {
                if (localEspetaculoDao.getItemExist(esp.getId_e()) == true) {
                    System.out.println("Atualizando espetaculo na base de dados local");
                    localEspetaculoDao.alter(esp);
                } else {
                    localEspetaculoDao.create(esp);
                }
            }
            localEspetaculoDao.close();
        }

        return listEspetaculo;
    }
}
