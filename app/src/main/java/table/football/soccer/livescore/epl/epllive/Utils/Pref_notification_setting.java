package table.football.soccer.livescore.epl.epllive.Utils;

import android.content.Context;
import android.content.SharedPreferences;



public class Pref_notification_setting {


    Context context;
    String TAG = "PrefManager_GAdsetting";

    public Pref_notification_setting(Context context) {
        this.context = context;
    }

    public void initSetting(){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("MyNotificationsettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //int TeamID[]={1,127,131,43,46,4,6,7,34,159,26,10,11,12,23,20,21,33,25,38};
        editor.putBoolean("1",   false);
        editor.putBoolean("127", false);
        editor.putBoolean("131", false);
        editor.putBoolean("43",  false);
        editor.putBoolean("46",  false);
        editor.putBoolean("4",   false);
        editor.putBoolean("6",   false);
        editor.putBoolean("7",   false);
        editor.putBoolean("34",  false);
        editor.putBoolean("159", false);
        editor.putBoolean("26",  false);
        editor.putBoolean("10",  false);
        editor.putBoolean("11",  false);
        editor.putBoolean("12",  false);
        editor.putBoolean("23",  false);
        editor.putBoolean("20",  false);
        editor.putBoolean("21",  false);
        editor.putBoolean("33",  false);
        editor.putBoolean("25",  false);
        editor.putBoolean("38",  false);
        editor.putBoolean("Match_Event",true);
        editor.apply();
        editor.commit();
    }

    public  void enableNotifcation(String ID){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("MyNotificationsettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ID,true);
        editor.apply();
        editor.commit();
    }

    public void disabeNotification(String ID){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("MyNotificationsettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ID,false);
        editor.apply();
        editor.commit();

    }

    public boolean isNotificationEnable(String ID){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("MyNotificationsettings", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ID,false);

    }





}
