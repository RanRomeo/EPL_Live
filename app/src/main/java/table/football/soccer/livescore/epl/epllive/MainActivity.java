package table.football.soccer.livescore.epl.epllive;

import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import io.fabric.sdk.android.Fabric;
import table.football.soccer.livescore.epl.epllive.Custom_dailog.fav_team_setting;
import table.football.soccer.livescore.epl.epllive.Fragments.Fragment_favTeam;
import table.football.soccer.livescore.epl.epllive.Fragments.Fragment_fixture;
import table.football.soccer.livescore.epl.epllive.Fragments.Fragment_home;
import table.football.soccer.livescore.epl.epllive.Fragments.Fragment_stats;
import table.football.soccer.livescore.epl.epllive.Fragments.Fragment_table;
import table.football.soccer.livescore.epl.epllive.Utils.Pref_fav_tem_settings;
import table.football.soccer.livescore.epl.epllive.Utils.workers.news_worker;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
public class MainActivity extends AppCompatActivity   {






    ImageButton home_button,fixture_button,myTeam_button,table_button,more_button;
    TextView home_text,fixture_text,myTeam_text,table_text,more_text;
    LinearLayout home_linear,fixture_linear,myTeam_linear,table_linear,more_linear;



   /// LinearLayout l;
   Boolean home_isAlive =null,fixture_isAlive=null,myTeam_isAlive=null,table_isAlive=null,more_isAlive=null;


    @Override
    protected void onStart() {
        super.onStart();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
       //         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
      //  WorkmagerStar();
        MobileAds.initialize(this, getResources().getString(R.string.GoogleAppID));
//  LinearLayout home_linear,fixture_linear,myTeam_linear,table_linear,more_linear;
        AudienceNetworkAds.initialize(getApplicationContext());
        AppEventsLogger.newLogger(getApplicationContext());


        //new InterAdsHelpers("MainActivityAds",getApplicationContext()).destroryFacebookAds();
        home_linear    = findViewById(R.id.home_linear);
        fixture_linear = findViewById(R.id.fixture_linear);
        myTeam_linear  = findViewById(R.id.fav_linear);
        table_linear   = findViewById(R.id.table_linear);
        more_linear    = findViewById(R.id.more_linear);


        home_button    = findViewById(R.id.home_button);
        fixture_button = findViewById(R.id.fixture_button);
        myTeam_button  = findViewById(R.id.my_team_button);
        table_button   = findViewById(R.id.table_button);
        more_button    = findViewById(R.id.more_button);

        home_text    = findViewById(R.id.home_text);
        fixture_text = findViewById(R.id.fixture_text);
        myTeam_text  = findViewById(R.id.my_team_text);
        table_text   = findViewById(R.id.table_text);
        more_text    = findViewById(R.id.more_text);


        AppRate.with(this)
                .setInstallDays(2) // default 10, 0 means install day.
                .setLaunchTimes(1) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(MainActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);

      //  WorkmagerStar();
        final Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //your code
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //Subscript to topic FCM
                        SubcripToNews();
                        SubcripTovideos();
                        SubcripToapp();
                        SubcripToevents();



                       if (new Pref_fav_tem_settings(getApplicationContext()).getFavTeam()==0){
                           fav_team_setting favTeamSetting_dailog = new fav_team_setting();
                           favTeamSetting_dailog.setCancelable(false);
                           favTeamSetting_dailog.show(getSupportFragmentManager(),"FavTeamSetting");
                       }

                        //Window window = favTeamSetting_dailog.g;
                        //window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                      //  Parameter.parameterOneVolley(SettingsActivity.this, getApplicationContext());
                    }
                });
            }
        }).start();

        //intialize notification
       // new Pref_notification_setting(getApplicationContext()).initSetting();

        AudienceNetworkAds.isInAdsProcess(this);
       // forceCrash();
        initDefaultView();
        intCustomBottomTextviewVIew();
        intCustomBottomVIew();


    }

//    public void forceCrash() {
//        throw new RuntimeException("This is a crash");
//    }

//    public void logSentFriendRequestEvent () {
//        logger.logEvent("sentFriendRequest");
//    }

    private void WorkmagerStar(){
//        OneTimeWorkRequest compressionWork =
//                new OneTimeWorkRequest.Builder(news_worker.class)
//                        .build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        PeriodicWorkRequest periodicNewsWorkRequest = new PeriodicWorkRequest.
                Builder(news_worker.class,15, TimeUnit.MINUTES,5, TimeUnit.MINUTES).setConstraints(constraints).build();


        WorkManager.getInstance().enqueue(periodicNewsWorkRequest);
    }

    private void SubcripToNews(){
        // [START subscribe_topics] newsData
        FirebaseMessaging.getInstance().subscribeToTopic("newsData")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                          //  msg = getString(R.string.msg_subscribe_failed);
                        //    Toast.makeText(MainActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
                        }else {
                       //    Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }

                       // Log.d(TAG, msg);
                      //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Topic_message", " " +e);
            }
        });
        // [END subscribe_topics]
    }

    private void SubcripTovideos(){
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("videos")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            //  msg = getString(R.string.msg_subscribe_failed);
                         //   Toast.makeText(MainActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
                        }else {
                         //   Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }

                        // Log.d(TAG, msg);
                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Topic_message", " " +e);
            }
        });
        // [END subscribe_topics]
    }


    private void SubcripToapp(){
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("app")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            //  msg = getString(R.string.msg_subscribe_failed);
                        //    Toast.makeText(MainActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
                        }else {
                         //   Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }

                        // Log.d(TAG, msg);
                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Topic_message", " " +e);
            }
        });
        // [END subscribe_topics]
    }


    private void SubcripToevents(){
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("events")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            //  msg = getString(R.string.msg_subscribe_failed);
                       //     Toast.makeText(MainActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
                        }else {
                        //    Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                        }

                        // Log.d(TAG, msg);
                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Topic_message", " " +e);
            }
        });
        // [END subscribe_topics]
    }

    //homefragment
    private void initDefaultView(){

        //Fragments
        Fragment fragment;
        fragment = new Fragment_home();
        //Method for simplfication
        loadFragment(fragment);
        home_button.setImageResource(R.drawable.ic_home_pink_24dp);
        home_text.setTextColor(getResources().getColor(R.color.colorAccent));
        home_isAlive = true;
    }


    private void intCustomBottomVIew(){
//  LinearLayout home_linear,fixture_linear,myTeam_linear,table_linear,more_linear;
        //ImageButton home_button,fixture_button,myTeam_button,table_button,more_button;
       // TextView home_text,,,table_text,more_text;
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (home_isAlive==null){
                    Fragment  fragment = new Fragment_home();
                    loadFragment(fragment);
                    home_isAlive = true;
                    fixture_isAlive=null;
                    myTeam_isAlive=null;
                    table_isAlive=null;
                    more_isAlive=null;
                }





                home_button.setImageResource(R.drawable.ic_home_pink_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));

            }
        });
        fixture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (fixture_isAlive==null){
                    Fragment fragment = new Fragment_fixture();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=true;
                    myTeam_isAlive=null;
                    table_isAlive=null;
                    more_isAlive=null;
                }



                fixture_button.setImageResource(R.drawable.ic_event_available_pink_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        myTeam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myTeam_isAlive==null){
                    Fragment fragment = new Fragment_favTeam();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=null;
                    myTeam_isAlive=true;
                    table_isAlive=null;
                    more_isAlive=null;
                }



                myTeam_button.setImageResource(R.drawable.ic_favorite_pink_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        table_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (table_isAlive==null){
                    Fragment  fragment = new Fragment_table();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=null;
                    myTeam_isAlive=null;
                    table_isAlive=true;
                    more_isAlive=null;
                }


                table_button.setImageResource(R.drawable.ic_chart_pink_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (more_isAlive==null){
                    Fragment   fragment = new Fragment_stats();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=null;
                    myTeam_isAlive=null;
                    table_isAlive=null;
                    more_isAlive=true;
                }



                more_button.setImageResource(R.drawable.ic_more_vert_pink_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

            }
        });



    }

    private void intCustomBottomTextviewVIew(){
//  LinearLayout home_linear,fixture_linear,myTeam_linear,table_linear,more_linear;
        //ImageButton home_button,fixture_button,myTeam_button,table_button,more_button;
        // TextView home_text,,,table_text,more_text;
        home_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (home_isAlive==null){
                    Fragment  fragment = new Fragment_home();
                    loadFragment(fragment);
                    home_isAlive = true;
                    fixture_isAlive=null;
                    myTeam_isAlive=null;
                    table_isAlive=null;
                    more_isAlive=null;
                }





                home_button.setImageResource(R.drawable.ic_home_pink_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));

            }
        });
        fixture_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (fixture_isAlive==null){
                    Fragment fragment = new Fragment_fixture();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=true;
                    myTeam_isAlive=null;
                    table_isAlive=null;
                    more_isAlive=null;
                }



                fixture_button.setImageResource(R.drawable.ic_event_available_pink_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        myTeam_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myTeam_isAlive==null){
                    Fragment fragment = new Fragment_favTeam();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=null;
                    myTeam_isAlive=true;
                    table_isAlive=null;
                    more_isAlive=null;
                }



                myTeam_button.setImageResource(R.drawable.ic_favorite_pink_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        table_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (table_isAlive==null){
                    Fragment  fragment = new Fragment_table();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=null;
                    myTeam_isAlive=null;
                    table_isAlive=true;
                    more_isAlive=null;
                }


                table_button.setImageResource(R.drawable.ic_chart_pink_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                more_button.setImageResource(R.drawable.ic_more_vert_black_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        more_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (more_isAlive==null){
                    Fragment   fragment = new Fragment_stats();
                    loadFragment(fragment);
                    home_isAlive = null;
                    fixture_isAlive=null;
                    myTeam_isAlive=null;
                    table_isAlive=null;
                    more_isAlive=true;
                }



                more_button.setImageResource(R.drawable.ic_more_vert_pink_24dp);
                more_text.setTextColor(getResources().getColor(R.color.colorAccent));
                ///-------------White------------------
                home_button.setImageResource(R.drawable.ic_home_black_24dp);
                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                myTeam_button.setImageResource(R.drawable.ic_favorite_selected_24dp);
                myTeam_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                fixture_button.setImageResource(R.drawable.ic_event_available_black_24dp);
                fixture_text.setTextColor(getResources().getColor(R.color.colorPrimary));

                table_button.setImageResource(R.drawable.ic_chart_black_24dp);
                table_text.setTextColor(getResources().getColor(R.color.colorPrimary));

            }
        });



    }

    //Framgents method
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flcontent, fragment);
        //transaction.addToBackStack(null);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }


}
