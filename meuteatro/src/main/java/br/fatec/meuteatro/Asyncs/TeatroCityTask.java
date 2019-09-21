package br.fatec.meuteatro.Asyncs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.fatec.meuteatro.banco.TeatroDAO;
import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael on 04/11/15.
 */
public class TeatroCityTask extends AsyncTask<String, Integer, List<TeatroBean>> {

    Context context;

    public AsyncTeatroResponse response = null;

    public TeatroCityTask (Activity activity) {
        context = activity.getBaseContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<TeatroBean> listTeatro) {
        response.listTeatroReturn(listTeatro);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<TeatroBean> doInBackground(String... params) {

        String city = params[0];
        TeatroDAO tDao = new TeatroDAO();
        List<TeatroBean> listTeatro;

        listTeatro = tDao.buscaTeatroPorCidade(city);

        //O SALVAMENTO LOCAL ESTA SENDO FEITO APENAS NA TELA T06, por isso não grava mais nesta task
//        localDao.open();
//        for(TeatroBean bean : listTeatro) {
//            if (localDao.getItemExist(bean.getId_t()) == true) {
//                System.out.println("Atualizando teatro na base de dados local");
//                localDao.alter(bean);
//            } else {
//                localDao.create(bean);
//            }
//        }
//        localDao.close();
//        System.out.println("Passei na nova task");

        return listTeatro;
    }
}
