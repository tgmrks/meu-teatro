package br.fatec.meuteatro.Asyncs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import br.fatec.meuteatro.banco.LocalTeatroDAO;
import br.fatec.meuteatro.banco.TeatroDAO;
import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael on 05/11/15.
 */
public class TeatroIdTask extends AsyncTask<Integer, Integer, TeatroBean> {

    Context context;

    public AsyncTeatroResponse response = null;

    public TeatroIdTask (Activity activity){
        context = activity.getBaseContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(TeatroBean bean) {
        response.itemTeatroReturn(bean);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected TeatroBean doInBackground(Integer... params) {

        int id = params[0];
        TeatroDAO tDao = new TeatroDAO();
        TeatroBean bean = new TeatroBean();
        LocalTeatroDAO localTeatroDao = new LocalTeatroDAO(context);

        bean = tDao.buscaTeatroPorID(id);

        localTeatroDao.open();
        if(localTeatroDao.getItemExist(bean.getId_t())){
            System.out.println("Atualizando teatro na base de dados local");
            localTeatroDao.alter(bean);
        } else {
            localTeatroDao.create(bean);
        }
        localTeatroDao.close();

        return bean;
    }
}
