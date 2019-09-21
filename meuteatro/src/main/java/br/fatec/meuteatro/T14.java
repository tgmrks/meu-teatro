package br.fatec.meuteatro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.Asyncs.AsyncTeatroResponse;
import br.fatec.meuteatro.Asyncs.TeatroCityTask;
import br.fatec.meuteatro.banco.LocalTeatroDAO;
import br.fatec.meuteatro.banco.MeusTeatrosDAO;
import br.fatec.meuteatro.banco.TeatroDAO;
import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael
 */

public class T14 extends Activity implements AsyncTeatroResponse {

    private MeusTeatrosDAO mtDao;
    private List<TeatroBean> listTeatro;
    //private TeatroDAO tDao;
    private Spinner spn1, spn2, spn3;
    private Button btnGravarTeatro;
    private LocalTeatroDAO localTeatroDao;
    private ProgressBar progress;

    SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t14);

        progress = (ProgressBar) findViewById(R.id.progressT14);

        spn1 = (Spinner)findViewById(R.id.spinnerAddUF);
        spn2 = (Spinner)findViewById(R.id.spinnerAddCidade);
        spn3 = (Spinner)findViewById(R.id.spinnerAddTeatro);

        btnGravarTeatro = (Button)findViewById(R.id.btnGravarTeatro);

        localTeatroDao = new LocalTeatroDAO(getBaseContext());

        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carregaSpinnerTeatro();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //criar metodo para onClick
        btnGravarTeatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mtDao = new MeusTeatrosDAO(getBaseContext());
                localTeatroDao = new LocalTeatroDAO(getBaseContext());
                localTeatroDao.open();
                mtDao.open();

                String clTeatro, clEstado, clCidade, clEndereco;
                int clId_t;

                String teatroSelecionado = spn3.getSelectedItem().toString();

                for(TeatroBean ttr : listTeatro){

                    if(ttr.getNome_teatro() == teatroSelecionado){

                        clTeatro = ttr.getNome_teatro();
                        clEstado = ttr.getUf();
                        clCidade = ttr.getCidade();
                        clEndereco  = ttr.getEndereco();
                        clId_t =  ttr.getId_t();

                        if(mtDao.getItemExist(ttr.getId_t()) == true){
                            Toast.makeText(getBaseContext(), "Teatro já cadastrado em sua lista", Toast.LENGTH_SHORT).show();
                        }else{
                            mtDao.create(clTeatro, clCidade, clEstado, clEndereco, clId_t);

                            if(localTeatroDao.getItemExist(ttr.getId_t()) == true){
                                System.out.println("Teatro já existe na base de dados");
                            }else{
                                localTeatroDao.create(ttr);
                            }

                            Intent i = new Intent(view.getContext(), T04.class);
                            startActivity(i);
                            finish();
                        }
                    }

                }

                mtDao.close();
                localTeatroDao.close();
            }
        });

        carregaSpinnerUF();
        carregaSpinnerCity();
        carregaSpinnerTeatro();
    }

    @Override //teatro
    public void fetchTeatroFinished(String output) {}

    @Override
    public void listTeatroReturn(List<TeatroBean> listBean) {
        String city = spn2.getSelectedItem().toString();

        listTeatro = new ArrayList<TeatroBean>();
        //ALIMENTANDO VARIAVEL PARA GRAVAR FAVORITOS
        if(listBean != null){
            for(TeatroBean bean : listBean){
                listTeatro.add(bean);
            }

            //Rever condição abaixo, se ainda é aplicavel
            if(listBean.get(0).getId_t() == 0){
                //O WS deve estar retornando 'id_t = 0' caso não haja retorno, ao invés de null.
                TeatroBean teatroVazio = new TeatroBean();
                String[] vazio = {"Não há teatro cadastrado para esta cidade"};
                teatroVazio.setCidade(city);
                teatroVazio.setUf(spn1.getSelectedItem().toString());
                listBean.set(0, teatroVazio);

                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, vazio);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn3.setClickable(false); btnGravarTeatro.setClickable(false);
                spn3.setAdapter(adapter);
            }
            else{
                int size = listBean.size();
                String[] teatros = new String[size];
                int i = 0;

                for(TeatroBean ttr : listBean){
                    String nomeTeatro;
                    nomeTeatro = ttr.getNome_teatro();
                    teatros[i] = nomeTeatro;
                    i++;
                }

                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, teatros);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn3.setClickable(true); btnGravarTeatro.setClickable(true);
                spn3.setAdapter(adapter);
            }

            spn3.setEnabled(true);
            btnGravarTeatro.setEnabled(true);
        }

        progress.setVisibility(View.GONE);

    }

    @Override
    public void itemTeatroReturn(TeatroBean bean) {}

    public void carregaSpinnerUF(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn1.setAdapter(adapter);
    }

    public void carregaSpinnerCity(){

        settings = getSharedPreferences(Utilities.PREFS_NAME, Context.MODE_PRIVATE);
        String city = settings.getString(Utilities.CIDADE, "");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cidades, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn2.setAdapter(adapter);

        if (!city.equals(null)) {
            int spinnerPostion = adapter.getPosition(city);
            spn2.setSelection(spinnerPostion);
            spinnerPostion = 0;
        }
    }

    public void carregaSpinnerTeatro(){

        progress.setVisibility(View.VISIBLE);
        spn3.setEnabled(false);
        btnGravarTeatro.setEnabled(false);

        String city = spn2.getSelectedItem().toString();
//************* BUSCA NO WS ************************************************************************
        boolean connAvailable = Connectivity.isConnected(this);
        TeatroCityTask task = new TeatroCityTask(this);
        task.response = this;
        task.execute(city);
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(getBaseContext(), T04.class);
        startActivity(it); finish();
    }

}



