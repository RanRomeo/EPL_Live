package table.football.soccer.livescore.epl.epllive.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ran on 05-Dec-18.
 */

public class Pref_subscirbe_match_setting  {

    Context context;
    String TAG = "MyMatch_subscribe";

    public Pref_subscirbe_match_setting(Context context) {
        this.context = context;
    }



    public  void enableNotifcation(String ID){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("MyMatch_subscribe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ID,true);
        editor.apply();
        editor.commit();
    }

    public void disabeNotification(String ID){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("MyMatch_subscribe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ID,false);
        editor.apply();
        editor.commit();

    }

    public boolean isNotificationEnable(String ID){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("MyMatch_subscribe", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ID,false);

    }






}
