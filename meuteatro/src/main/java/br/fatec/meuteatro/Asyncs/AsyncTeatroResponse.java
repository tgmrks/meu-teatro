package br.fatec.meuteatro.Asyncs;

import java.util.List;

import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael on 04/11/15.
 */
public interface AsyncTeatroResponse {

    void fetchTeatroFinished(String output);
    void listTeatroReturn(List<TeatroBean> listBean);
    void itemTeatroReturn(TeatroBean bean);
}
