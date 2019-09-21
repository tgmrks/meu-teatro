package br.fatec.meuteatro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import br.fatec.meuteatro.Asyncs.AsyncEspetaculoResponse;
import br.fatec.meuteatro.Asyncs.AsyncTeatroResponse;
import br.fatec.meuteatro.Asyncs.EspetaculoCityTask;
import br.fatec.meuteatro.Asyncs.EspetaculoTeatroTask;
import br.fatec.meuteatro.Asyncs.TeatroCityTask;
import br.fatec.meuteatro.banco.LocalEspetaculoDAO;
import br.fatec.meuteatro.banco.LocalTeatroDAO;
import br.fatec.meuteatro.banco.MeusTeatrosDAO;
import br.fatec.meuteatro.banco.MinhasCidadesDAO;
import br.fatec.meuteatro.beans.EspetaculoBean;
import br.fatec.meuteatro.beans.MeusTeatrosBean;
import br.fatec.meuteatro.beans.T06HeaderBean;
import br.fatec.meuteatro.beans.T06ItemBean;
import br.fatec.meuteatro.beans.TeatroBean;

/**
 * Created by ismael
 */

public class T06 extends Activity implements AsyncTeatroResponse, AsyncEspetaculoResponse{

    private ProgressBar progress;
    private TextView txtVazio;
    private ListView listItemEspetaculo;
    private List<EspetaculoBean> espetaculos;

    private TeatroBean teatro;
    private MeusTeatrosBean mtBean;

    private LocalTeatroDAO localTeatroDao;
    private LocalEspetaculoDAO localEspetaculoDao;
    private MinhasCidadesDAO mcDao;
    private MeusTeatrosDAO mtDao;

    private SharedPreferences settings;
    private Bundle parametro;
    private Intent i;
    private String city;
    private String path;
    private int id = 0;
    private long daysDiff;
    private int count = 0;
    private boolean updatetatus = false;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t06);

        settings = getSharedPreferences(Utilities.PREFS_NAME, Context.MODE_PRIVATE);
        city = settings.getString(Utilities.CIDADE, "");

        localEspetaculoDao = new LocalEspetaculoDAO(getBaseContext());
        localTeatroDao = new LocalTeatroDAO(getBaseContext());
        mcDao = new MinhasCidadesDAO(getBaseContext());
        mtDao = new MeusTeatrosDAO(getBaseContext());

        txtVazio = (TextView)findViewById(R.id.textVazio);
        txtVazio.setVisibility(View.INVISIBLE);
        //pega a referencia da listView
        listItemEspetaculo = (ListView)findViewById(R.id.listPecaTeatro);

        progress = (ProgressBar) findViewById(R.id.progressT06);

        //define o Listener quando alguem clicar no item
        listItemEspetaculo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("POSIÇÃO: " + position);
                EspetaculoBean bean = new EspetaculoBean();
                //bean = espetaculos.get(position -1);
                int id_e_view = view.getId();
                System.out.println("id da view: " + id_e_view);
                //COMPARAR A POSIÇÃO COM O LISTOBJECT ???
                //OU CRIAR UM MAPA DE POSIÇÕES PARA OS HEADER, acrescentando +1 sempre q encontrar um...
                Intent it = new Intent(getBaseContext(), T07.class);
                Bundle parametro = new Bundle();
                //parametro.putInt("id", bean.getId_e());
                parametro.putInt("id", id_e_view);
                it.putExtras(parametro);
                startActivity(it); //finish();
            }
        });

        i = getIntent();
        if(i.getStringExtra("param").equals("teatro")){
            //System.out.println(">>> CAMINHO = teatro >>>");
            path = "teatro";
            parametro = i.getExtras();
            id = parametro.getInt("id");
            System.out.println("id recuperado: " + id);
            if(i.getBooleanExtra("vazio", false)){
                //System.out.println("veio de VAZIO");
                buscaEspetaculoTeatro(true);
            }
            //System.out.println("NÃO veio de VAZIO");
        }
        else{
            //System.out.println(">>> CAMINHO = cidade >>>");
            path = "cidade";
        }
        //SE SALDO >= PERIODO, ENTÃO busca no WS,SENÃO segue fluxo normal
        String lastUpdate = settings.getString(Utilities.LAST_UPDATE, "");
        boolean updateRequired = Utilities.getUpdateRequired(this);

        if(!updateRequired){
            System.out.println("Dentro do periodo");
            carregarDados();
        }
        else{
            updatetatus = true;
            //altera todos os CHECKEDS e chamar fluxo normal
            System.out.println("Fora do periodo, atualizar!");
            mcDao.open();
            mcDao.alterAllChecked(0);
            mcDao.close();

            mtDao.open();
            //e se não existir teatro?
            mtDao.alterAllChecked(0);
            mtDao.close();
//            settings.edit().putString(Utilities.LAST_UPDATE, nowSimpleString).commit();
//            System.out.println("Novo last update: " + settings.getString(Utilities.LAST_UPDATE, ""));
            carregarDados();
        }
    }

//****************** VERIFICA SE CARRAGA POR CIDADE OU TEATRO *****************
    private void carregarDados(){
        espetaculos = new ArrayList<EspetaculoBean>();

        if(path.equals("teatro")){
            System.out.println(">>> CAMINHO = teatro >>>");
            verificarTeatro();

        }
        else{
            System.out.println(">>> CAMINHO = cidade >>>");
            verificarCidade();
        }
    }
    public void verificarCidade(){
        mcDao.open();
        //não é feita a verificação se a cidadeexiste no BD, pois, se ela já foi selecionada, estará na base, se não, há um erro no código
        String[] item = mcDao.getItem(city);//quando estiver funcionando, trocar por um metodo isChecked
        int cidade_checked = Integer.valueOf(item[3]);
        //System.out.println("valor de cidade[3]: " + cidade_checked);
        if(cidade_checked != 1){
            System.out.println(">>> CAMINHO = E >>>");
            //ver melhor momento para esta alteração
            //mcDao.alterChecked(city, 1);//criar um alterarChecked
            mcDao.close();
            buscaTeatroCity(false);
        }else{
            System.out.println(">>> CAMINHO = F >>>");
            //carrega teatro local
            mcDao.close();

            carregarAdapter();
        }
    }
    public void verificarTeatro(){
        mtDao.open();
        mtBean = new MeusTeatrosBean();
        mtBean = mtDao.getItem(id);
        int teatro_checked = mtBean.getChecked();

        if(teatro_checked != 1){
            mtDao.close();

            String cidade_bean = mtBean.getCidade();
            mcDao.open();
            //Se cidade do Teatro selecionado (T04) existir localmente e já tiver feito cache dos espetáculos,
            // então não precisa buscar no WS, do contrário (não existe localmente ou checked = 0) busca no WS.
            //aqui apenas evitamos a busca desnecessária dos dados
            if(mcDao.getItemExist(cidade_bean)){
                String[] cidade_local = mcDao.getItem(cidade_bean);
                int cidade_local_checked = Integer.valueOf(cidade_local[3]);
                if(cidade_local_checked != 1){
                    System.out.println(">>> CAMINHO = A >>>");
                    mcDao.close();
                    buscaEspetaculoTeatro(false);
                }
                else{//cidade_checked = 1
                    System.out.println(">>> CAMINHO = B >>>");
                    mcDao.close();
                    carregarAdapter();
                }
            }else{//cidade do not exist
                System.out.println(">>> CAMINHO = C >>>");
                mcDao.close();
                buscaEspetaculoTeatro(false);
            }
        }
        else{//teatro_checked = 1
            System.out.println(">>> CAMINHO = D >>>");
            mtDao.close();

            carregarAdapter();
        }
    }
//************** CHAMADA DAS ASYNCTASKS ****************
    public void buscaTeatroCity(boolean update){
        boolean conn = Utilities.verifyConnection(this);
        if(conn != true){//eu aborto e fico na tela onde estou ?????
            //se esta vindo de update
                //ficar na tela anterio, não carregar nada, pq o contexto é T06
            //senão
                //mostrar tela T06Vazio
            if(update){
                //System.out.println("Fica na mesma tela");
            }
            else{
                //System.out.println("cidade não é verificada");
                chamarVazio(0, path);
            }
        }
        else{
            //colocar esta alteração 1 passo antes?
            progress.setVisibility(View.VISIBLE);
            TeatroCityTask task2 = new TeatroCityTask(this);
            task2.response = this;
            task2.execute(city);
        }
    }
    public void buscaEspetaculoCity(){
        EspetaculoCityTask task1 = new EspetaculoCityTask(this);
        task1.response = this;
        task1.execute(city);
    }
    public void buscaEspetaculoTeatro(boolean update){

        boolean conn = Utilities.verifyConnection(this);
        if(conn != true){
            if(update){
                //System.out.println("Fica na mesma tela");
            }
            else{
                chamarVazio(0, path);
            }
        }
        else{
            //colocar esta alteração 1 passo antes?
            progress.setVisibility(View.VISIBLE);
            EspetaculoTeatroTask task = new EspetaculoTeatroTask(this);
            task.response = this;
            task.execute(id);
        }
    }

//************** RETURN TEATROS ************************
    @Override
    public void fetchTeatroFinished(String output) {}
    @Override
    public void listTeatroReturn(List<TeatroBean> listBean) {

        if (listBean != null) {

            updatetatus = false;

            if (listBean.get(0).getId_t() != 0){
                mcDao.open();
                mcDao.alterChecked(city, 1);
                mcDao.close();

                localTeatroDao.open();
                for (TeatroBean ttr : listBean) {
                    if (localTeatroDao.getItemExist(ttr.getId_t()) == true) {
                        System.out.println("Atualizando teatro na base de dados local");
                        localTeatroDao.alter(ttr);
                    } else {
                        localTeatroDao.create(ttr);
                    }
                }
                localTeatroDao.close();
                buscaEspetaculoCity();
            }
            else{
                chamarVazio(1, "cidade");
            }
        } else {

            if(updatetatus == true){
                updatetatus = false;
                progress.setVisibility(View.GONE);
            }
            else {

                System.out.println("----------> Retornando null");
                chamarVazio(0, "cidade");
            }
        }
    }

    @Override
    public void itemTeatroReturn(TeatroBean bean) {}
    //************** RETURN ESPETACULO ************************
    @Override
    public void fetchEspetaculoFinished(String output) {}

    @Override
    public void listEspetaculoReturn(List<EspetaculoBean> listBean) {
    //***********************VER *****************************
    //este tratamento deve ser alterado, o WS não deve retornar nada quando não encontrado
        ///////////////////////////////////////////////////////////////
        if(listBean != null) {
            updatetatus = false;

            if(path == "teatros"){
                mtDao.open();
                mtBean.setChecked(1);
                mtDao.alter(mtBean);
                mtDao.close();
            }
            if (listBean.get(0).getId_e() != 0) {
                carregarAdapter();
            } else {
                chamarVazio(1, "cidade");
            }
        }
        else{
            if(updatetatus == true){
                updatetatus = false;
                progress.setVisibility(View.GONE);
            }
            else {
                System.out.println("----------> Retornando null");
                chamarVazio(0, "cidade");
            }
        }
    }
    @Override
    public void itemEspetaculoReturn(EspetaculoBean bean) {}

//************ FINALIZA PROCESSO DE CARREGAMENTO ******************
    public void carregarAdapter(){
        espetaculos = new ArrayList<EspetaculoBean>();

        localEspetaculoDao.open();
        if(path.equals("teatro")){
            System.out.println(">>>>>>>>>> Buscou por Teatro - " + id);
            espetaculos = localEspetaculoDao.getItemTeatro(id);
        }else{
            System.out.println(">>>>>>>>>> Buscou por Cidade - " + city);
            espetaculos = localEspetaculoDao.getItemCity(city);
        }
        localEspetaculoDao.close();

        System.out.println("LIST SIZE: " + espetaculos.size());


        List<Object> listObj = new ArrayList<Object>();
        int lastId_t = 0;

        if (espetaculos.size() > 0) {
            localTeatroDao.open();
            for (EspetaculoBean esp : espetaculos) {
                T06ItemBean item = new T06ItemBean();

                item.setId_e(esp.getId_e());
                item.setTxtTitulo(esp.getTitulo());
                item.setTxtEntrada(esp.getEntrada());
                item.setTxtClassificacao(esp.getClassificacao());
                item.setTxtDataHora(esp.getData_hora());
                item.setImg(esp.getImagem());

                int currentId_t = esp.getId_t();
                teatro = localTeatroDao.getItemId(currentId_t);

                T06HeaderBean header = new T06HeaderBean();

                header.setTxtTeatro(teatro.getNome_teatro());
                header.setTxtCidade(teatro.getCidade());
                header.setTxtUf(teatro.getUf());

                if(lastId_t == 0 || lastId_t != currentId_t){
                    //System.out.println("Novo teatro: " + teatro.getId_t());
                    listObj.add(header);
                    listObj.add(item);
                }
                else{
                    //System.out.println("mesmo teatro: " + teatro.getId_t());
                    listObj.add(item);
                }

//                System.out.println("last Id_t: " + lastId_t);
//                System.out.println("current Id_t: " + currentId_t);
//                System.out.println("------------------");
                lastId_t = currentId_t;
            }
            txtVazio.setVisibility(View.INVISIBLE);
            localTeatroDao.close();
        } else {
            System.out.println("----------> Retornando null");
            chamarVazio(1, "cidade");
        }
//        System.out.println("Teste list obj: " + listObj.get(0).getClass().getName());
//        T06HeaderBean h = (T06HeaderBean) listObj.get(0);
//        System.out.println("teste cast obj: " + h.getTxtTeatro());
        T06Adapter adapter = new T06Adapter(this, listObj);
        listItemEspetaculo.setAdapter(adapter);

        progress.setVisibility(View.GONE);
    }

    //********************* MENU DE OPÇÕES *****************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        //menu.getItem(0).setVisible(false);//escolhe item do menu a ser "desabilitado"
        return true; //a boa prática manda que seja retornado a chamada super da classe
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                startActivity(new Intent(this, T06.class).putExtra("param", "cidade"));  finish();
                break;
            case R.id.action_update:
                updateT06();
                break;
            case R.id.action_add:
                startActivity(new Intent(this, T04.class)); finish();
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

    public void updateT06(){
        updatetatus = true;
        if(path.equals("teatro")){
        System.out.println("update TEATRO");
            buscaEspetaculoTeatro(updatetatus);
        }
        else{
        System.out.println("update CIDADE");;
            buscaTeatroCity(updatetatus);
        }
    }
    public void chamarVazio(int p_code, String p_path){
        progress.setVisibility(View.GONE);
        txtVazio.setVisibility(View.VISIBLE);

        if(p_code == 0){
            //erro de conexão
            txtVazio.setText(R.string.noConnectionMessage2);
        }
        else{
            //não há conteudo
            txtVazio.setText(R.string.noContentMessage);
        }
    }

    @Override
    public void onBackPressed() {
        if(count<1){
            count++;
            Toast.makeText(this, R.string.stringQuitQuestion, Toast.LENGTH_SHORT).show();
        }
        else{
            finish();
        }
    }

}
