package br.fatec.meuteatro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.fatec.meuteatro.Asyncs.AgendaTask;
import br.fatec.meuteatro.Asyncs.AsyncAgendaResponse;
import br.fatec.meuteatro.banco.EspetaculoDAO;
import br.fatec.meuteatro.banco.LocalAgendaDAO;
import br.fatec.meuteatro.banco.LocalEspetaculoDAO;
import br.fatec.meuteatro.banco.LocalTeatroDAO;
import br.fatec.meuteatro.beans.AgendaBean;
import br.fatec.meuteatro.beans.EspetaculoBean;
import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael
 */

public class T07 extends Activity implements AsyncAgendaResponse{

    private TeatroBean teatroBean;
    private EspetaculoBean espetaculoBean;
    private List<AgendaBean> agendaBeans;

    private EspetaculoDAO eDao;
    private LocalEspetaculoDAO localEspetaculoDao;
    private LocalTeatroDAO localTeatroDao;
    private LocalAgendaDAO localAgendaDao;

    private TextView edtTeatro;
    private TextView edtCidade;
    private TextView edtUf;
    private TextView edtTitulo;
    private TextView edtDescricao;
    private TextView edtEntrada;
    private TextView edtDataHora;
    private TextView edtEndereco;
    private ImageView img;
    private ImageView imgClassificacao;

    private Button btnComprar;
    private int id;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.t07);

        edtTeatro = (TextView)findViewById(R.id.txtViewMeusTeatrosTeatro);
        edtCidade = (TextView)findViewById(R.id.txtViewDetalheCidade);
        edtUf = (TextView)findViewById(R.id.txtViewDetalheUf);
        edtTitulo = (TextView)findViewById(R.id.txtViewDetalheTitulo);
        edtDescricao = (TextView)findViewById(R.id.txtViewDetalheDescricao);
        edtEntrada = (TextView)findViewById(R.id.txtViewDetalheEntrada);
        edtDataHora = (TextView)findViewById(R.id.txtViewDetalheDataHora);
        edtEndereco = (TextView)findViewById(R.id.txtViewDetalheEndereco);

        img = (ImageView)findViewById(R.id.imageViewDetalhe);
        imgClassificacao = (ImageView)findViewById(R.id.imageViewClassificacao2);
        btnComprar = (Button)findViewById(R.id.btnDetalheComprar);

        Intent i = getIntent();
        Bundle parametro = i.getExtras();

        id = parametro.getInt("id");

        carregarDados();

    }

    public void carregarDados(){
//************* BUSCA LOCAL ************************************************************************
        localEspetaculoDao = new LocalEspetaculoDAO(getBaseContext());
        espetaculoBean = new EspetaculoBean();

        localEspetaculoDao.open();
        espetaculoBean = localEspetaculoDao.getItemId(id);
        localEspetaculoDao.close();

        localTeatroDao = new LocalTeatroDAO(getBaseContext());
        teatroBean = new TeatroBean();

        int id_t = espetaculoBean.getId_t();

        localTeatroDao.open();
        teatroBean = localTeatroDao.getItemId(id_t);
        localTeatroDao.close();

//************* BUSCA NO WS ************************************************************************
//  ATIVAR A CHAMADA DE AGENDA QUANDO HOUVER TRATAMENTO PARA OS DADOS
//        AgendaTask task = new AgendaTask(this);
//        task.response = this;
//        task.execute(id);
        //Os  dados de "Agenda" ainda não estão em uso, portanto somente há a busca

//************* Alimentando Views ******************************************************************
        //dados de teatro
        edtTeatro.setText(teatroBean.getNome_teatro());
        edtCidade.setText(" - " + teatroBean.getCidade());
        edtUf.setText("(" + teatroBean.getUf() + ")");
        edtEndereco.setText(" " + teatroBean.getEndereco());

        //dados de espetaculo
        edtTitulo.setText(espetaculoBean.getTitulo());
        edtDescricao.setText(espetaculoBean.getDescricao());
        edtEntrada.setText(" " + espetaculoBean.getEntrada());
        //edtClassificacao.setText(" " + espetaculoBean.getClassificacao());
        edtDataHora.setText(" " + espetaculoBean.getData_hora());

        //botão comprar
        String str = espetaculoBean.getLink_externo().toString();
        if(str.equals("none") || str.equals("null") || str.equals("") || str.equals(null) ){
            //System.out.println("ehhh null ----------------");
            btnComprar.setEnabled(false);
            View b = findViewById(R.id.btnDetalheComprar);
            b.setEnabled(false);

        }else{
            //System.out.println("diferente de null ------------------- ex: " + espetaculoBean.getLink_externo());
            btnComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri uri = Uri.parse(espetaculoBean.getLink_externo());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(launchBrowser);
                }
            });
        }

        Bitmap bMap = BitmapFactory.decodeByteArray(espetaculoBean.getImagem(), 0, espetaculoBean.getImagem().length);
        img.setImageBitmap(bMap);

        int cl = 0;
        cl = Integer.parseInt(espetaculoBean.getClassificacao());

        switch(cl){
            case 0:
                imgClassificacao.setImageResource(R.drawable.c_0);
                break;
            case 10:
                imgClassificacao.setImageResource(R.drawable.c_10);
                break;
            case 12:
                imgClassificacao.setImageResource(R.drawable.c_12);
                break;
            case 14:
                imgClassificacao.setImageResource(R.drawable.c_14);
                break;
            case 16:
                imgClassificacao.setImageResource(R.drawable.c_16);
                break;
            case 18:
                imgClassificacao.setImageResource(R.drawable.c_18);
                break;
        }

    }

    @Override
    public void fetchAgendaFinished(String output) {
        System.out.println("Status AgendaTask: " + output);
    }

    @Override
    public void listAgendaReturn(List<AgendaBean> listBean) {
        //Os  dados de "Agenda" ainda não estão em uso, portanto somente há a busca
    }

    //********************* MENU DE OPÇÕES *****************************************
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.custom_menu_2, menu);
//        return true;
//    }
//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_cart3:
//                chamaCalendario();
//                break;
//            case R.id.action_cart2:
//                onBackPressed();
//                break;
//            case R.id.action_cart1:
//                startActivity(new Intent(this, T04.class)); finish();
//                break;
//            case R.id.mn_config:
//                startActivity(new Intent(this, T03.class)); finish();
//                //Toast.makeText(this, "configuraçoes", Toast.LENGTH_LONG).show();
//                break;
//            case R.id.mn_teatros:
//                startActivity(new Intent(this, T04.class)); finish();
//                break;
//            case R.id.mn_sobre:
//                startActivity(new Intent(this, T17.class)); finish();
//                break;
//            case R.id.sair:
//                finish();
//                break;
//        }
//        return super.onMenuItemSelected(featureId, item);
//    }
    //*****************************************************************************
//    @Override
//    public void onBackPressed() {
//
//        if (count < 1) {
//            count++;
//            Toast.makeText(this, R.string.stringQuitQuestion, Toast.LENGTH_LONG).show();
//        } else {
//            finish();
//        }
//    }

}