package table.football.soccer.livescore.epl.epllive.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

public class PrefManager_facebookInterAd_settings {

    Context context;

    public PrefManager_facebookInterAd_settings(Context context) {
        this.context = context;
    }

    //used when ad is clicked
    public void SaveClickedTime(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFacebookInter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long SaveTime = System.currentTimeMillis();
        // Log.d(TAG, "Time Saved After Clciked: " + SaveTime);
        editor.putLong("LastTimeClicked",SaveTime);
        //editor.putBoolean("FB_adclick",true);
        editor.apply();
        //editor.commit();

        //Disable
        disableFacebookAd();
    }

    //Facebook ad is disable
    public  void disableFacebookAd(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFacebookInter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Log.d(TAG, "Time Saved After Clciked: " + SaveTime);
        // editor.putLong("LastTimeClicked",SaveTime);
        editor.putBoolean("FaceBook_Ad",false);
        editor.apply();
        //  editor.commit();
    }

    //
    public  void EnableFacebookAd(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFacebookInter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Log.d(TAG, "Time Saved After Clciked: " + SaveTime);
        // editor.putLong("LastTimeClicked",SaveTime);
        editor.putBoolean("FaceBook_Ad",true);
        editor.apply();
        // editor.commit();
    }


    public Boolean CheckAdCanShow(){

        int AdShowInterval = 10;

        if (getTimeDiff_Mins()>AdShowInterval){
            EnableFacebookAd();
            return  true;
        }else
            return false;


    }



    public Boolean CheckDisableFacebook(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFacebookInter", Context.MODE_PRIVATE);
        Boolean result = sharedPreferences.getBoolean("FaceBook_Ad",true);
        return  result;
    }


    public long GetLastClickedTime(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFacebookInter", Context.MODE_PRIVATE);
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
