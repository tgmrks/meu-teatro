package br.fatec.meuteatro.banco;

import android.util.Base64;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.fatec.meuteatro.Utilities;
import br.fatec.meuteatro.beans.AgendaBean;
import br.fatec.meuteatro.beans.EspetaculoBean;

/**
 * Created by ismael on 16/04/15.
 */
public class EspetaculoDAO {

    //private static final String URL = "http://192.168.0.102:8080/MeuTeatroWS/services/EspetaculoDAO?wsdl";
    private static final String URL = "http://ec2-54-233-83-237.sa-east-1.compute.amazonaws.com:8080/MeuTeatroWS/services/EspetaculoDAO?wsdl";

    private static final String NAMESPACE = "http://meuteatrows.com.br";
    //metodos de interação com banco
    private static final String BUSCAR_TODOS = "buscaTodosEspetaculo";
    private static final String BUSCAR_POR_CIDADE = "buscaEspetaculoPorCidade";
    private static final String BUSCAR_POR_TEATRO = "buscaEspetaculoPorTeatro";
    private static final String BUSCAR_POR_ID = "buscaEspetaculoPorID";
    private static final String BUSCAR_AGENDA = "buscaAgenda";


    public List<EspetaculoBean> buscaTodosEspetaculo(){

        List<EspetaculoBean> lista = new ArrayList<EspetaculoBean>();

        //representação do soap
        SoapObject buscaTodosEspetaculo = new SoapObject(NAMESPACE, BUSCAR_TODOS);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaTodosEspetaculo);
        envelope.implicitTypes = true;

        //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);
        try {
            http.call("urn:" + BUSCAR_TODOS, envelope);


            if(envelope.getResponse() instanceof SoapObject){

                SoapObject resposta = (SoapObject) envelope.getResponse();

                EspetaculoBean usr = new EspetaculoBean();
                usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                usr.setTitulo(resposta.getProperty("titulo").toString());
                usr.setDescricao(resposta.getProperty("descricao").toString());
                usr.setData_hora(resposta.getProperty("data_hora").toString());
                usr.setClassificacao(resposta.getProperty("classificacao").toString());
                usr.setEntrada(resposta.getProperty("entrada").toString());
                usr.setLink_externo(resposta.getProperty("link_externo").toString());
                usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                String foto = resposta.getProperty("imagem").toString();
                //converte a imagem (na hora de inserir esta sendo convertido com MarshalBase64())
                byte[] bt = Base64.decode(foto, Base64.DEFAULT);
                usr.setImagem(bt);
                lista.add(usr);
            }
            else {
                Vector<SoapObject> retorno = (Vector<SoapObject>) envelope.getResponse();

                //for each - para cada campo de 'resposta'
                for (SoapObject resposta : retorno) {

                    EspetaculoBean usr = new EspetaculoBean();
                    usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                    usr.setTitulo(resposta.getProperty("titulo").toString());
                    usr.setDescricao(resposta.getProperty("descricao").toString());
                    usr.setData_hora(resposta.getProperty("data_hora").toString());
                    usr.setClassificacao(resposta.getProperty("classificacao").toString());
                    usr.setEntrada(resposta.getProperty("entrada").toString());
                    usr.setLink_externo(resposta.getProperty("link_externo").toString());
                    usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                    //pega a propriade/conteudo da foto (imagem em bytes) e grava como String
                    String foto = resposta.getProperty("imagem").toString();
                    //converte a imagem (na hora de inserir esta sendo convertido com MarshalBase64())
                    byte[] bt = Base64.decode(foto, Base64.DEFAULT);
                    usr.setImagem(bt);
                    lista.add(usr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    public List<EspetaculoBean> buscaEspetaculoPorCidade(String cidade){

        List<EspetaculoBean> lista = new ArrayList<EspetaculoBean>();

        //representação do soap
        SoapObject buscaEspetaculoPorCidade = new SoapObject(NAMESPACE, BUSCAR_POR_CIDADE);
        buscaEspetaculoPorCidade.addProperty("cidade", cidade);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaEspetaculoPorCidade);
        envelope.implicitTypes = true;

        //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);
        try {
            http.call("urn:" + BUSCAR_POR_CIDADE, envelope);

            if(envelope.getResponse() instanceof SoapObject){

                SoapObject resposta = (SoapObject) envelope.getResponse();

                EspetaculoBean usr = new EspetaculoBean();
                usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                usr.setTitulo(resposta.getProperty("titulo").toString());
                usr.setDescricao(resposta.getProperty("descricao").toString());
                usr.setData_hora(resposta.getProperty("data_hora").toString());
                usr.setClassificacao(resposta.getProperty("classificacao").toString());
                usr.setEntrada(resposta.getProperty("entrada").toString());
                usr.setLink_externo(resposta.getProperty("link_externo").toString());
                usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                //pega a propriade/conteudo da foto (imagem em bytes) e grava como String
                String foto = resposta.getProperty("imagem").toString();
                //converte a imagem (na hora de inserir esta sendo convertido com MarshalBase64())
                byte[] bt = Base64.decode(foto, Base64.DEFAULT);
                usr.setImagem(bt);
                lista.add(usr);
            }
            else {
                Vector<SoapObject> retorno = (Vector<SoapObject>) envelope.getResponse();

                //for each - para cada campo de 'resposta'
                for (SoapObject resposta : retorno) {

                    EspetaculoBean usr = new EspetaculoBean();
                    usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                    usr.setTitulo(resposta.getProperty("titulo").toString());
                    usr.setDescricao(resposta.getProperty("descricao").toString());
                    usr.setData_hora(resposta.getProperty("data_hora").toString());
                    usr.setClassificacao(resposta.getProperty("classificacao").toString());
                    usr.setEntrada(resposta.getProperty("entrada").toString());
                    usr.setLink_externo(resposta.getProperty("link_externo").toString());
                    usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                    //pega a propriade/conteudo da foto (imagem em bytes) e grava como String
                    String foto = resposta.getProperty("imagem").toString();
                    //converte a imagem (na hora de inserir esta sendo convertido com MarshalBase64())
                    byte[] bt = Base64.decode(foto, Base64.DEFAULT);
                    usr.setImagem(bt);
                    lista.add(usr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    public List<EspetaculoBean> buscaEspetaculoPorTeatro(int id_t){

        List<EspetaculoBean> lista = new ArrayList<EspetaculoBean>();

        //representação do soap
        SoapObject buscaEspetaculoPorTeatro = new SoapObject(NAMESPACE, BUSCAR_POR_TEATRO);
        buscaEspetaculoPorTeatro.addProperty("id_t", id_t);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaEspetaculoPorTeatro);
        envelope.implicitTypes = true;

        //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);
        try {
            http.call("urn:" + BUSCAR_POR_TEATRO, envelope);

            if(envelope.getResponse() instanceof SoapObject){

                SoapObject resposta = (SoapObject) envelope.getResponse();

                EspetaculoBean usr = new EspetaculoBean();
                usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                usr.setTitulo(resposta.getProperty("titulo").toString());
                usr.setDescricao(resposta.getProperty("descricao").toString());
                usr.setData_hora(resposta.getProperty("data_hora").toString());
                usr.setClassificacao(resposta.getProperty("classificacao").toString());
                usr.setEntrada(resposta.getProperty("entrada").toString());
                usr.setLink_externo(resposta.getProperty("link_externo").toString());
                usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                //pega a propriade/conteudo da foto (imagem em bytes) e grava como String
                String foto = resposta.getProperty("imagem").toString();
                //converte a imagem (na hora de inserir esta sendo convertido com MarshalBase64())
                byte[] bt = Base64.decode(foto, Base64.DEFAULT);
                usr.setImagem(bt);
                lista.add(usr);
            }
            else {
                Vector<SoapObject> retorno = (Vector<SoapObject>) envelope.getResponse();

                //for each - para cada campo de 'resposta'
                for (SoapObject resposta : retorno) {

                    EspetaculoBean usr = new EspetaculoBean();
                    usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                    usr.setTitulo(resposta.getProperty("titulo").toString());
                    usr.setDescricao(resposta.getProperty("descricao").toString());
                    usr.setData_hora(resposta.getProperty("data_hora").toString());
                    usr.setClassificacao(resposta.getProperty("classificacao").toString());
                    usr.setEntrada(resposta.getProperty("entrada").toString());
                    usr.setLink_externo(resposta.getProperty("link_externo").toString());
                    usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                    //pega a propriade/conteudo da foto (imagem em bytes) e grava como String
                    String foto = resposta.getProperty("imagem").toString();
                    //converte a imagem (na hora de inserir esta sendo convertido com MarshalBase64())
                    byte[] bt = Base64.decode(foto, Base64.DEFAULT);
                    usr.setImagem(bt);
                    lista.add(usr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    public EspetaculoBean buscaEspetaculoPorID(int id){

        EspetaculoBean usr = null;

        //representação do soap
        SoapObject buscaEspetaculoPorID = new SoapObject(NAMESPACE, BUSCAR_POR_ID);
        buscaEspetaculoPorID.addProperty("id", id);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaEspetaculoPorID);
        envelope.implicitTypes = true;

            //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);
        try {
            http.call("urn:" + BUSCAR_POR_ID, envelope);

            SoapObject resposta = (SoapObject) envelope.getResponse();

            usr = new EspetaculoBean();
            usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
            usr.setTitulo(resposta.getProperty("titulo").toString());
            usr.setDescricao(resposta.getProperty("descricao").toString());
            usr.setData_hora(resposta.getProperty("data_hora").toString());
            usr.setClassificacao(resposta.getProperty("classificacao").toString());
            usr.setEntrada(resposta.getProperty("entrada").toString());
            usr.setLink_externo(resposta.getProperty("link_externo").toString());
            usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
            //pega a propriade/conteudo da foto (imagem em bytes) e grava como String
            String foto = resposta.getProperty("imagem").toString();
            //converte a imagem (na hora de inserir esta sendo convertido com MarshalBase64())
            byte[] bt = Base64.decode(foto, Base64.DEFAULT);
            usr.setImagem(bt);
//            usr.setDia(Integer.parseInt(resposta.getProperty("dia").toString()));
//            usr.setMes(Integer.parseInt(resposta.getProperty("mes").toString()));
//            usr.setAno(Integer.parseInt(resposta.getProperty("ano").toString()));
//            usr.setHora(resposta.getProperty("hora").toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return usr;
    }


    public List<AgendaBean> buscaAgenda(int id){

        List<AgendaBean> lista = new ArrayList<AgendaBean>();

        //representação do soap
        SoapObject buscaAgenda = new SoapObject(NAMESPACE, BUSCAR_POR_CIDADE);
        buscaAgenda.addProperty("id", id);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaAgenda);
        envelope.implicitTypes = true;

        //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);
        try {
            http.call("urn:" + BUSCAR_AGENDA, envelope);

            if(envelope.getResponse() instanceof SoapObject){

                SoapObject resposta = (SoapObject) envelope.getResponse();

                AgendaBean usr = new AgendaBean();
                usr.setId_a(Integer.parseInt(resposta.getProperty("id_a").toString()));
                usr.setDia(Integer.parseInt(resposta.getProperty("dia").toString()));
                usr.setMes(Integer.parseInt(resposta.getProperty("mes").toString()));
                usr.setAno(Integer.parseInt(resposta.getProperty("ano").toString()));
                usr.setHora(resposta.getProperty("hora").toString());
                usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                lista.add(usr);
            }
            else {
                Vector<SoapObject> retorno = (Vector<SoapObject>) envelope.getResponse();

                //for each - para cada campo de 'resposta'
                for (SoapObject resposta : retorno) {

                    AgendaBean usr = new AgendaBean();
                    usr.setId_a(Integer.parseInt(resposta.getProperty("id_a").toString()));
                    usr.setDia(Integer.parseInt(resposta.getProperty("dia").toString()));
                    usr.setMes(Integer.parseInt(resposta.getProperty("mes").toString()));
                    usr.setAno(Integer.parseInt(resposta.getProperty("ano").toString()));
                    usr.setHora(resposta.getProperty("hora").toString());
                    usr.setId_e(Integer.parseInt(resposta.getProperty("id_e").toString()));
                    lista.add(usr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

}
