package br.fatec.meuteatro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.fatec.meuteatro.banco.MinhasCidadesDAO;

public class T03 extends Activity {

    /*********************************************
     * App Meu Teatro                             *
     * Autoria: Alexsandro Martins Dias         *
     * Hugo FellipeTrevelin Benevides  *
     * Ismael Thiago Marques           *
     *********************************************/

    SharedPreferences settings;
    MinhasCidadesDAO mcDao;

    private Spinner spn1, spn2;
    private CheckBox chkFeedWifi;
    private RadioGroup rdgUpdate;
    private RadioButton rdbUpdateOp1, rdbUpdateOp2, rdbUpdateOp3;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t03);

        spn1 = (Spinner) findViewById(R.id.spinnerConfigUF);
        spn2 = (Spinner) findViewById(R.id.spinnerConfigCidade);

        chkFeedWifi = (CheckBox) findViewById(R.id.chkBoxFeedWifi);

        rdgUpdate = (RadioGroup) findViewById(R.id.rdGroupUpdateFeed);
        rdbUpdateOp1 = (RadioButton) findViewById(R.id.rdbUpdateOp1);
        rdbUpdateOp2 = (RadioButton) findViewById(R.id.rdbUpdateOp2);
        rdbUpdateOp3 = (RadioButton) findViewById(R.id.rdbUpdateOp3);

        settings = getSharedPreferences(Utilities.PREFS_NAME, Context.MODE_PRIVATE);

        chkFeedWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("CheckBox verificado !!!");
                if(buttonView.isChecked()){
                    System.out.println("CheckBox marcado !!!");
                    settings.edit().putBoolean(Utilities.FEED_WIFI, true).commit();
                }
                else{
                    System.out.println("CheckBox desmarcado !!!");
                    settings.edit().putBoolean(Utilities.FEED_WIFI, false).commit();
                }
            }
        });

        rdgUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int periodo = 7;
                System.out.println("Periodicidade ja salva: " + settings.getInt(Utilities.PERIODO, 7));

                if(checkedId == rdbUpdateOp1.getId()){
                    periodo = 7;
                    System.out.println("Periodicidade de:  7 dias");
                }
                else if(checkedId == rdbUpdateOp2.getId()){
                    periodo = 15;
                    System.out.println("Periodicidade de:  15 dias");
                }
                else if(checkedId == rdbUpdateOp3.getId()){
                    periodo = 30;
                    System.out.println("Periodicidade de:  30 dias");
                }
                else {
                    System.out.println("Periodicidade não selecionada");
                }

                settings.edit().putInt(Utilities.PERIODO, periodo).commit();
                //System.out.println("Periodicidade editada: " + settings.getString(PERIODO, ""));
            }
        });

        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//***************** GRAVA CONFIGURAÇÕES *****************
                String city = spn2.getSelectedItem().toString();
                String uf = spn1.getSelectedItem().toString();//usar trim para pegar so siglas dos estados
                settings.edit().putString(Utilities.CIDADE, city).commit();
//************* GRAVA CIDADE NA BASE DE DADOS P/ VERIFICACAO DE UPDATE DE FEED
                mcDao = new MinhasCidadesDAO(getBaseContext());
                mcDao.open();
                if(mcDao.getItemExist(city)){
                    System.out.println("Cidade já existe na base de dados");
                }
                else{
                    mcDao.create(city, uf);
                }
                mcDao.close();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        carregarSpinnerUF();
        carregarSpinnerCity();
        carregarCheckBoxesDados();
        carregarRadioGroupUpdate();

        if(settings.getBoolean(Utilities.FIRST_TIME, true)) {
            startTur();
            settings.edit().putBoolean(Utilities.FIRST_TIME, false).commit();
        }
    }

    private void carregarSpinnerUF() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn1.setAdapter(adapter);
    }

    private void carregarSpinnerCity() {
        //FAZER CARREGAR DO WS P/ TRAZER APENAS CIDADES CADASTRADAS
//        if (settings.getBoolean("my_first_time_config", true)) {//carrega direto do res
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cidades, android.R.layout.simple_spinner_dropdown_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spn2.setAdapter(adapter);
//        } else {//carrega a cidade selecionada
            String city = settings.getString(Utilities.CIDADE, "");
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cidades, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn2.setAdapter(adapter);

            if (!city.equals("")) {
                int spinnerPostion = adapter.getPosition(city);
                spn2.setSelection(spinnerPostion);
                spinnerPostion = 0;
            }
        //}
    }

    public void carregarCheckBoxesDados(){
//        if (settings.getBoolean("my_first_time_config", true)){
//            chkFeedWifi.setChecked(false);
//        }
//        else{
            if(settings.getBoolean(Utilities.FEED_WIFI, false) != true){
                chkFeedWifi.setChecked(false);
            }
            else{
                chkFeedWifi.setChecked(true);
            }
        //}
    }

    public void carregarRadioGroupUpdate(){
//        if (settings.getBoolean("my_first_time_config", true)){
//            //atribuir/criar sharedPreferences aqui e desativa na MainActivity
//            rdbUpdateOp1.setChecked(true);
//        }
//        else{
            int periodo = settings.getInt(Utilities.PERIODO, 7);
            if(periodo == 30){
                rdbUpdateOp3.setChecked(true);
            }
            else if(periodo == 15) {
                rdbUpdateOp2.setChecked(true);
            }
            else{
                rdbUpdateOp1.setChecked(true);
            }
        //}
    }

    //********************* MENU DE OPÇÕES *****************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        menu.getItem(1).setVisible(false);//escolhe item do menu a ser "desabilitado"
        menu.getItem(3).setVisible(false);//escolhe item do menu a ser "desabilitado"
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(this, T06.class).putExtra("param", "cidade"));
                finish();
                break;
            case R.id.action_add:
                startActivity(new Intent(this, T04.class));
                finish();
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, T03.class));
                finish();
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

    @Override
    public void onBackPressed() {
        if (count < 1) {
            count++;
            Toast.makeText(this, R.string.stringQuitQuestion, Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    public void startTur(){
        messageWarning();
    }

    public void messageWarning(){
//        AlertDialog.Builder alerta = new AlertDialog.Builder(T03.this);
//        alerta.setMessage(R.string.turMessageWarning)
//                .setPositiveButton(R.string.turNext, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        messageWhatIs();
//                    }
//                });
//        alerta.show();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView imgIcon = (ImageView) dialog.findViewById(R.id.dialogIcon);
        imgIcon.setImageResource(android.R.drawable.ic_dialog_alert);
        TextView txtTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
        txtTitle.setText(R.string.turTitleWarning);
        TextView txtMessage = (TextView)dialog.findViewById(R.id.dialogMessage);
        txtMessage.setText(R.string.turMessageWarning);
        Button btnNext = (Button)dialog.findViewById(R.id.dialogButtonNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                messageWhatIs(); dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void messageWhatIs(){
//        AlertDialog.Builder alerta = new AlertDialog.Builder(T03.this);
//        alerta.setMessage(R.string.turMessageWhatIs)
//                .setPositiveButton(R.string.turNext, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        messageConfig();
//                    }
//                });
//        alerta.show();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView imgIcon = (ImageView) dialog.findViewById(R.id.dialogIcon);
        imgIcon.setImageResource(R.mipmap.ic_launcher);
        TextView txtTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
        txtTitle.setText(R.string.app_name);
        TextView txtMessage = (TextView)dialog.findViewById(R.id.dialogMessage);
        txtMessage.setText(R.string.turMessageWhatIs);
        Button btnNext = (Button)dialog.findViewById(R.id.dialogButtonNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                messageConfig(); dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void messageConfig(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
            ImageView imgIcon = (ImageView) dialog.findViewById(R.id.dialogIcon);
            imgIcon.setImageResource(R.drawable.ic_action_settings);
        TextView txtTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
        txtTitle.setText(R.string.config);
        TextView txtMessage = (TextView)dialog.findViewById(R.id.dialogMessage);
            txtMessage.setText(R.string.turMessageConfig);
            Button btnNext = (Button)dialog.findViewById(R.id.dialogButtonNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                messageHome(); dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void messageHome(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView imgIcon = (ImageView) dialog.findViewById(R.id.dialogIcon);
        imgIcon.setImageResource(R.drawable.ic_action_home);
        TextView txtTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
        txtTitle.setText(R.string.turTitleHome);
        TextView txtMessage = (TextView)dialog.findViewById(R.id.dialogMessage);
        txtMessage.setText(R.string.turMessageHome);
        Button btnNext = (Button)dialog.findViewById(R.id.dialogButtonNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                messageFavorites();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void messageFavorites(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView imgIcon = (ImageView) dialog.findViewById(R.id.dialogIcon);
        imgIcon.setImageResource(R.drawable.ic_action_add);
        TextView txtTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
        txtTitle.setText(R.string.meus_teatros);
        TextView txtMessage = (TextView)dialog.findViewById(R.id.dialogMessage);
        txtMessage.setText(R.string.turMessageFavorites);
        Button btnNext = (Button)dialog.findViewById(R.id.dialogButtonNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                messageEnd(); dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void messageEnd(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(T03.this);
        alerta.setMessage(R.string.turMessageEnd1)
                .setPositiveButton(R.string.turMessageEnd2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });
        alerta.show();
    }
}
