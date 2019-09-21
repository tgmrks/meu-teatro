package br.fatec.meuteatro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    /********************************************
     * App: Meu Teatro                           *
     * Autoria: Alexsandro Martins Dias          *
     *          Hugo FellipeTrevelin Benevides   *
     *          Ismael Thiago Marques            *
     *                                           *
     * Desenvolvimento: Ismael Thiago Marques    *
     *********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);

        Thread splash_screen = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME, 0);
                    if (settings.getBoolean(Utilities.FIRST_TIME, true)) {
                    //como default é TRUE, na 1a vez, como o my_first_time não existe, irá funcionar.
                    //Assm que for alterado para false, não será mais passado neste if
                        Log.d("Comments", "First time");
                        //************ SALVANDO UMA DATA INICIAL PARA UPDATE DE FEED ***************
                        //PODE VIRAR UMA CLASSE EXTERNA, POIS ESTA SE REPETINDO NA T06
                        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DATE, 0);//acrescentar valores negativos para teste de update
                        Date now = calendar.getTime();
                        String nowSimpleString = simpleDate.format(now);
                        //System.out.println("Teste com Calendar: " + calendar.getTime());
                        settings.edit().putString(Utilities.LAST_UPDATE, nowSimpleString).commit();
                        settings.edit().putBoolean(Utilities.FEED_WIFI, false).commit();
                        // first time task
                        startActivity(new Intent(getApplicationContext(), T03.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        //record the fact that the app has been started at least once
                        //settings.edit().putBoolean(Utilities.FIRST_TIME, false).commit();
                    }
                    else{
                        Intent it = new Intent(getApplicationContext(), T06.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        it.putExtra("param", "cidade");//colocar constante
                        startActivity(it);
                        finish();
                    }
                }
            }
        };
        splash_screen.start();
    }
}
