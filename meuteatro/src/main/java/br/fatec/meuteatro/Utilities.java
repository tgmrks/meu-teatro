package br.fatec.meuteatro;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ismael on 19/11/15.
 */
public class Utilities {

    //WS
    public static int TIMEOUT = 30000;//TIMEOUT PADRÃO DO SOAP É DE 20000ms
    public static int LONG_TIMEOUT = 40000;
    public static int EXTRA_TIMEOUT = 60000;

    //SharedPreferences
    public static final String PREFS_NAME = "MyPrefs";
    public static final String CIDADE = "cidade_key";
    public static final String PERIODO = "periodo_key";
    public static final String LAST_UPDATE = "last_feed_update_key";
    public static final String FIRST_TIME = "my_first_time";
    public static final String FEED_WIFI = "feed_wifi_key";

    public static boolean getUpdateRequired(Context context){
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int daysDiff = 0;

        //formatando padrao de data
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        String nowSimpleString = simpleDate.format(new Date().getTime());

        //Obtendo data atual
        Date now = null;
        try {
            now = simpleDate.parse(nowSimpleString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println("NOW = String > Date .getTime() result = " + nowSimpleString);
        System.out.println("NOW = Date > Date .getTime() result = " + now);

        String lastUpdate = settings.getString(Utilities.LAST_UPDATE, "");

        //Obtendo data do ultimo update
        Date then = null;
        try {
            then = simpleDate.parse(lastUpdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println("THEN = String > Date .getTime() result = " + lastUpdate);
        System.out.println("THEN Date > Date .getTime() result = " + then);

        //**** FAZER CALCULO PARA OBTER "SALDO"

        long diff = now.getTime() - then.getTime();
//        System.out.println("Date Calc =  milliseconds: " + diff);
//        long diffSeconds = diff / 1000 % 60;
//        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000) % 24;
        daysDiff = (int) (diff / (24 * 60 * 60 * 1000));
        System.out.println(daysDiff + " days, ");

        int periodo = settings.getInt(Utilities.PERIODO, 0);

        if(daysDiff < periodo){
            return false;
        }
        else{
            settings.edit().putString(Utilities.LAST_UPDATE, nowSimpleString).commit();
            //System.out.println("Novo last update: " + settings.getString(Utilities.LAST_UPDATE, ""));
            return true;
        }

        //return daysDiff;
    }

    public static boolean verifyConnection(Context context){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        boolean connAvailable;
        boolean wifiAvailable;
        boolean onlyWifiChecked;

        onlyWifiChecked = settings.getBoolean(FEED_WIFI, false);

        connAvailable = Connectivity.isConnected(context);
        System.out.println("Conexao: " + connAvailable);
        wifiAvailable = Connectivity.isConnectedWifi(context);
        System.out.println("Wi-Fi: " + wifiAvailable);

        if(connAvailable != true){
            Toast.makeText(context, R.string.noConnectionMessage, Toast.LENGTH_LONG).show();
            //se houver conteudo local, carrega-lo
            return false;
        }else{
            System.out.println("Conectado a internet !!!");
            if(onlyWifiChecked == true){
                if(wifiAvailable != true){
                    //se houver conteudo local, carrega-lo
                    Toast.makeText(context, R.string.noMobileConnectionPermission, Toast.LENGTH_LONG).show();
                    return false;
                }
                else{
                    System.out.println("utilizando WiFi !!!");
                    //busca WS
                    return true;
                }
            }
            else{
                //buscar WS
                return true;
            }
        }
    }


}
