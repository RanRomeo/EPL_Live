package table.football.soccer.livescore.epl.epllive.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ran on 14-Nov-18.
 */

public class Pref_fav_tem_settings {


    Context context;
    String TAG = "PrefManager_GAdsetting";

    public Pref_fav_tem_settings(Context context) {
        this.context = context;
    }

    //0 means no favTeam

    public  void SaveFavTeam(int ID){
        SharedPreferences sharedPreferences = context.getSharedPreferences("My_Fav_Team_Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("MyClub",ID);
        editor.apply();
        editor.commit();
    }


    public int getFavTeam(){
        SharedPreferences  sharedPreferences = context.getSharedPreferences("My_Fav_Team_Settings", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("MyClub",0);
    }

}
