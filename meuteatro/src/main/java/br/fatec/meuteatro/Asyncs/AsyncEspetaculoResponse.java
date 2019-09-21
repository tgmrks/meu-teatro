package br.fatec.meuteatro.Asyncs;

import java.util.List;

import br.fatec.meuteatro.beans.EspetaculoBean;

/**
 * Created by ismael on 05/11/15.
 */
public interface AsyncEspetaculoResponse {

    void fetchEspetaculoFinished (String output);
    void listEspetaculoReturn(List<EspetaculoBean> listBean);
    void itemEspetaculoReturn(EspetaculoBean bean);
}
