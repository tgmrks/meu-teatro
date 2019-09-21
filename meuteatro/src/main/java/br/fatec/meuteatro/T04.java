package br.fatec.meuteatro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.banco.MeusTeatrosDAO;
import br.fatec.meuteatro.beans.MeusTeatrosBean;
import br.fatec.meuteatro.beans.T04Bean;

/**
 * Created by ismael
 */

public class T04 extends Activity{

private int count = 0;

	private Button btnAdd;
    private SharedPreferences settings;

    private ListView listaMeusTeatros;
    private List<MeusTeatrosBean> meusTeatros;
    private List<T04Bean> t04Beans;
    private T04Bean t04Bean;
    private MeusTeatrosDAO mtDao;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.t04);

        meusTeatros = null;

        //*******TESTE DE GRAVAÇÃO DAS CONFIG.*******
        settings = getSharedPreferences(Utilities.PREFS_NAME, Context.MODE_PRIVATE);
        String city = settings.getString(Utilities.CIDADE, "");

        listaMeusTeatros = (ListView)findViewById(R.id.listViewMeusTeatros);

		btnAdd = (Button)findViewById(R.id.btnAddTeatro);

        listaMeusTeatros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MeusTeatrosBean mtBean = meusTeatros.get(position);

                Bundle parametro = new Bundle();
                Intent i = new Intent(getBaseContext(), T06.class);
                parametro.putInt("id", mtBean.getId_t());
                i.putExtras(parametro);
                i.putExtra("param", "teatro");
                startActivity(i);
                finish();
            }
        });

		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				
				Intent i = new Intent(v.getContext(), T14.class);
               // Intent i = new Intent(v.getContext(), T01.class);
				startActivity(i);finish();
			}
		});

        carregarMeusTeatros();
	}

    public void carregarMeusTeatros(){

        //SELECIONAR OS DADOS DE UMA BASE DE DADOS SQLite
        mtDao = new MeusTeatrosDAO(getBaseContext());

        mtDao.open();
        meusTeatros = mtDao.getAllItems();
        mtDao.close();

        t04Beans = new ArrayList<T04Bean>();

        if(t04Beans != null) {
            for (MeusTeatrosBean mtb : meusTeatros) {

                t04Bean = new T04Bean();
                t04Bean.set_id(mtb.get_id());
                t04Bean.setTxtTeatro(mtb.getNome_teatro());
                t04Bean.setTxtCidade(mtb.getCidade());
                t04Bean.setTxtUF(mtb.getUf());
                t04Bean.setTxtEndereco(mtb.getEndereco());
                t04Bean.setId_t(mtb.getId_t());
                t04Beans.add(t04Bean);
            }

            T04Adapter adapter = new T04Adapter(this, t04Beans);
            listaMeusTeatros.setAdapter(adapter);
        }
    }

    //********************* MENU DE OPÇÕES *****************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        menu.getItem(1).setVisible(false);//escolhe item do menu a ser "desabilitado"
        menu.getItem(2).setVisible(false);//escolhe item do menu a ser "desabilitado"
        return true;
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                startActivity(new Intent(this, T06.class).putExtra("param", "cidade"));  finish();
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, T03.class)); finish();
                //Toast.makeText(this, "configuraçoes", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_about:
                startActivity(new Intent(this, T17.class));
                break;
            case R.id.sair:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    //*****************************************************************************
//
    @Override
    public void onBackPressed() {

        if (count < 1) {
            count++;
            Toast.makeText(this, R.string.stringQuitQuestion, Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}