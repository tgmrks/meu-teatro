package br.fatec.meuteatro.banco;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.fatec.meuteatro.Utilities;
import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael on 16/04/15.
 */
public class TeatroDAO {


    //private static final String URL = "http://192.168.0.102:8080/MeuTeatroWS/services/TeatroDAO?wsdl";
    private static final String URL = "http://ec2-54-233-83-237.sa-east-1.compute.amazonaws.com:8080/MeuTeatroWS/services/TeatroDAO?wsdl";

    private static final String NAMESPACE = "http://meuteatrows.com.br";
    //metodos de interação com banco
    private static final String BUSCAR_TODOS = "buscaTodosTeatro";
    private static final String BUSCAR_POR_ID = "buscaTeatroPorID";
    private static final String BUSCAR_POR_CIDADE = "buscaTeatroPorCidade";


    public List<TeatroBean> buscaTodosTeatro(){

        List<TeatroBean> lista = new ArrayList<TeatroBean>();

        //representação do soap
        SoapObject buscaTodosTeatro = new SoapObject(NAMESPACE, BUSCAR_TODOS);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaTodosTeatro);
        envelope.implicitTypes = true;

        //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);
        try {
            http.call("urn:" + BUSCAR_TODOS, envelope);


            if(envelope.getResponse() instanceof SoapObject){

                SoapObject resposta = (SoapObject) envelope.getResponse();

                TeatroBean usr = new TeatroBean();
                usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                usr.setNome_teatro(resposta.getProperty("nome_teatro").toString());
                usr.setEndereco(resposta.getProperty("endereco").toString());
                usr.setCidade(resposta.getProperty("cidade").toString());
                usr.setUf(resposta.getProperty("uf").toString());

                lista.add(usr);
            }
            else {
                Vector<SoapObject> retorno = (Vector<SoapObject>) envelope.getResponse();

                //for each - para cada campo de 'resposta'
                for (SoapObject resposta : retorno) {

                    TeatroBean usr = new TeatroBean();
                    usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                    usr.setNome_teatro(resposta.getProperty("nome_teatro").toString());
                    usr.setEndereco(resposta.getProperty("endereco").toString());
                    usr.setCidade(resposta.getProperty("cidade").toString());
                    usr.setUf(resposta.getProperty("uf").toString());

                    lista.add(usr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    public TeatroBean buscaTeatroPorID(int id){

        TeatroBean usr = null;

        //representação do soap
        SoapObject buscaTeatroPorID = new SoapObject(NAMESPACE, BUSCAR_POR_ID);
        buscaTeatroPorID.addProperty("id", id);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaTeatroPorID);
        envelope.implicitTypes = true;

        //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);
        try {
            http.call("urn:" + BUSCAR_POR_ID, envelope);

            SoapObject resposta = (SoapObject) envelope.getResponse();

            usr = new TeatroBean();
            usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
            usr.setNome_teatro(resposta.getProperty("nome_teatro").toString());
            usr.setEndereco(resposta.getProperty("endereco").toString());
            usr.setCidade(resposta.getProperty("cidade").toString());
            usr.setUf(resposta.getProperty("uf").toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return usr;
    }

    public List<TeatroBean> buscaTeatroPorCidade(String cidade){

        List<TeatroBean> lista = new ArrayList<TeatroBean>();

        //representação do soap
        SoapObject buscaTeatroCidade = new SoapObject(NAMESPACE, BUSCAR_POR_CIDADE);
        buscaTeatroCidade.addProperty("cidade", cidade);

        //"envelopa" soapObject para enviar ao WS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscaTeatroCidade);
        envelope.implicitTypes = true;

        //Dispará para o WS
        HttpTransportSE http = new HttpTransportSE(URL, Utilities.TIMEOUT);//timeout padrão é de 20000ms
        try {
            http.call("urn:" + BUSCAR_POR_CIDADE, envelope);

            if(envelope.getResponse() instanceof SoapObject){

                SoapObject resposta = (SoapObject) envelope.getResponse();

                TeatroBean usr = new TeatroBean();
                usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                usr.setNome_teatro(resposta.getProperty("nome_teatro").toString());
                usr.setEndereco(resposta.getProperty("endereco").toString());
                usr.setCidade(resposta.getProperty("cidade").toString());
                usr.setUf(resposta.getProperty("uf").toString());

                lista.add(usr);
            }
            else {
                Vector<SoapObject> retorno = (Vector<SoapObject>) envelope.getResponse();
                //for each - para cada campo de 'resposta'
                for (SoapObject resposta : retorno) {
                    TeatroBean usr = new TeatroBean();
                    usr.setId_t(Integer.parseInt(resposta.getProperty("id_t").toString()));
                    usr.setNome_teatro(resposta.getProperty("nome_teatro").toString());
                    usr.setEndereco(resposta.getProperty("endereco").toString());
                    usr.setCidade(resposta.getProperty("cidade").toString());
                    usr.setUf(resposta.getProperty("uf").toString());

                    lista.add(usr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //criar um finally?
        return lista;
    }

}
