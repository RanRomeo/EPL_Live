package table.football.soccer.livescore.epl.epllive.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ran on 27-Sep-18.
 */

public class PrefManager_googleAdSetting {

    Context context;
    String TAG = "PrefManager_GAdsetting";
    public PrefManager_googleAdSetting(Context context) {
        this.context = context;
    }


    //used when ad is clicked
    public void SaveClickedTime(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyGoogleAdsettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long SaveTime = System.currentTimeMillis();
       // Log.d(TAG, "Time Saved After Clciked google: " + SaveTime);
        editor.putLong("LastTimeClicked",SaveTime);
        //editor.putBoolean("FB_adclick",true);
        editor.apply();
       // editor.commit();

        //Disable
        disableGoogleAd();
    }


    public  void disableGoogleAd(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyGoogleAdsettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Log.d(TAG, "Time Saved After Clciked: " + SaveTime);
        // editor.putLong("LastTimeClicked",SaveTime);
        editor.putBoolean("Google_Ad",false);
        editor.apply();
        //editor.commit();
    }

    //
    public  void EnableGoogleAd(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyGoogleAdsettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Log.d(TAG, "Time Saved After Clciked: " + SaveTime);
        // editor.putLong("LastTimeClicked",SaveTime);
        editor.putBoolean("Google_Ad",true);
        editor.apply();
        //editor.commit();
    }


    public Boolean CheckAdCanShow(){
        //time after ad will be visible
        int AdShowInterval = 5;
        if (getTimeDiff_Mins()>AdShowInterval){
            EnableGoogleAd();
            return  true;
        }else
            return false;


    }



    public Boolean CheckDisableGoogleAd(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyGoogleAdsettings", Context.MODE_PRIVATE);
        Boolean result = sharedPreferences.getBoolean("Google_Ad",true);
        return  result;
    }


    public long GetLastClickedTime(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyGoogleAdsettings", Context.MODE_PRIVATE);
        Long LastTimeLogClciked = sharedPreferences.getLong("LastTimeClicked",0);
        return  LastTimeLogClciked;
    }

    public long getTimeDiff_Mins(){
        Long CurrentTime = System.currentTimeMillis();
        Long TimeDiff = CurrentTime -  GetLastClickedTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(TimeDiff);
      //  Log.d(TAG, "UserClickedTimeDiff: " + TimeDiff  + "  Current Time :  " + CurrentTime + " LasTime " + GetLastClickedTime());
        return minutes;
    }




}
