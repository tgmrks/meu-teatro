package br.fatec.meuteatro.Asyncs;

import java.util.List;

import br.fatec.meuteatro.beans.AgendaBean;

/**
 * Created by ismael on 06/11/15.
 */
public interface AsyncAgendaResponse {

    void fetchAgendaFinished(String output);
    void listAgendaReturn(List<AgendaBean> listBean);
}
